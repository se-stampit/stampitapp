package com.jku.stampit.Services;

import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jku.stampit.StampItApplication;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Store;
import com.jku.stampit.dto.CardDTO;
import com.jku.stampit.dto.CompanyDTO;
import com.jku.stampit.dto.CountDTO;
import com.jku.stampit.dto.StampCodeDTO;
import com.jku.stampit.utils.Constants;
import com.jku.stampit.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by user on 04/05/16.
 * Class to retrieve and store all available cards for companies etc
 * this one should communicate with the backend server
 */
public class CardManager {
    public static String CARDS_UPDATE_MESSAGE = "CARD_UPDATE";
    private static String UNVERIFIED_STAMPES_KEY = "UNVERIFIED_STAMPS";
    private static CardManager instance;
    private final List<StampCard> mycards = new ArrayList<StampCard>();
    private final List<Company> availableCompanies = new ArrayList<Company>();
    private  LocalBroadcastManager broadcaster;
    private SharedPreferences settings;
    //List for unverified Tokens, which must be sent to server
    //We dont know Syntax in Application for Security Reasons
    private ObjectMapper mapper;

    private final Set<String> unverifiedStampTokens = new HashSet<String>();

    public interface CardManagerScanListener
    {
        public void ScanSuccessfull(Integer statusCode );
    }
    public interface CardManagerCardUpdateCallback
    {
        public void CardsUpdated(List<StampCard> newCards);
    }

    public interface CardManagerStampUpdateCallback
    {
        public void StampsUpdated();
    }

    public static CardManager getInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new CardManager();
        }
        return instance;
    }
    private CardManager() {
        mapper = new ObjectMapper();
        broadcaster = LocalBroadcastManager.getInstance(StampItApplication.getContext());
        mycards.addAll(getDummyCards());
        availableCompanies.addAll(getDummyCompanies());

        Log.d("Cardmanager", "constructor call");
        //LoadMyStampCardsFromServer();
        LoadProvidersFromServer();
        settings = StampItApplication.getContext().getSharedPreferences(StampItApplication.APP_PREFERENCES, 0);
        unverifiedStampTokens.addAll(settings.getStringSet(UNVERIFIED_STAMPES_KEY, new HashSet<String>()));
    }

    public void SaveSettings() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(UNVERIFIED_STAMPES_KEY, unverifiedStampTokens);
    }
    public List<StampCard> GetMyCards() {
       return mycards;
    }
    public List<Company> GetAvailableCompanies() {
        return availableCompanies;
    }

    private List<Company> getDummyCompanies() {
        List<Company> dummyCompanies = new ArrayList<Company>();

        Company comp = new Company(UUID.randomUUID().toString(),"FUSSAL",null);
        Company comp1 = new Company(UUID.randomUUID().toString(),"Pizza Hut",null);

        dummyCompanies.add(comp);
        dummyCompanies.add(comp1);

        Store st1 = new Store("asdf","ljöj","Geschäft 1","Altenbergerstraße 2/3,4020 Linz",new Date(),new Date(),48.3366136,14.3171166);
        Store st2 = new Store("asdf1","ljöj1","Geschäft 2","Altenbergerstraße 14/3,4020 Linz",new Date(),new Date(),48.3466136,14.3171166);
        Store st3 = new Store("asdf2","ljöj2","Geschäft 3","Altenbergerstraße 6/3,4020 Linz",new Date(),new Date(),48.3366136,14.3271166);
        Store st4 = new Store("asdf3","ljöj3","Geschäft 4","Altenbergerstraße 10/1,4020 Linz",new Date(),new Date(),48.4166136,14.3171166);
        Store st5 = new Store("asdf4","ljöj4","Geschäft 5","Altenbergerstraße 5/1,4020 Linz",new Date(),new Date(),48.3666136,14.3271166);
        Store st6 = new Store("asdf5","ljöj5","Geschäft 6","Altenbergerstraße 8/3,4020 Linz",new Date(),new Date(),48.3066136,14.3671166);
        Store st7 = new Store("asdf6","ljöj6","Geschäft 7","Altenbergerstraße 23/3,4020 Linz",new Date(),new Date(),48.3366136,14.3171166);

        comp.AddStore(st1);
        comp.AddStore(st2);
        comp.AddStore(st3);
        comp1.AddStore(st4);
        comp1.AddStore(st5);
        comp1.AddStore(st6);
        comp1.AddStore(st7);
        return dummyCompanies;
    }
    private List<StampCard> getDummyCards() {

        Company comp = new Company(UUID.randomUUID().toString(),"FUSSAL",null);
        Company comp1 = new Company(UUID.randomUUID().toString(),"Pizza Hut",null);

        List<StampCard> stampCards = new ArrayList<StampCard>();
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Yogurt",comp, "Ein gratis Yogurt",5,2,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Kebap",comp1, "Ein gratis Kebap",7,0,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"FrozenYogurt",comp, "Ein gratis Frozen Yogurt",10,10,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Pizza",comp1, "Eine gratis Pizza",10,10,new Date(), new Date(), 100000,false));
        return stampCards;
    }

    public void DeleteCard(String cardID) {
        StampCard card = GetMyCardForID(cardID);
        if(card != null) {
            card.setDeleteDate(new Date());
        }
    }
   public List<StampCard> getCardsForCompanyID(String compID){
       List<StampCard> cards = new ArrayList<StampCard>();
       //TODO Generate Company Cards from companies? howto?
       return cards;
    }
    public void ScanStamp(String qrCode, final CardManagerScanListener callback){
        if(qrCode.isEmpty())
            return;

        Map<String,String> header = new HashMap<String,String>();
        header.put("auth", UserManager.getInstance().getSessionToken());
        StampCodeDTO code = new StampCodeDTO();
        code.setStampcode(qrCode);
        String json = "";
        Boolean returnvalue = false;
        try {
            json = mapper.writeValueAsString(code);
            Log.d(this.getClass().toString(), "JsonString:\r\n" + json);

            if (Utils.HasInternetConnection(StampItApplication.getContext())) {

                new HttpPostJsonRequest(header) {
                    @Override
                    protected void onPostExecute(WebserviceReturnObject result) {
                        if (result.getStatusCode() == Constants.HTTP_RESULT_OK) {

                        } else {
                            unverifiedStampTokens.add(contentToSend);
                            //TODO Return something went wrong
                        }
                        if(callback != null) {
                            callback.ScanSuccessfull(result.getStatusCode());
                        }
                    }
                }.execute(Constants.ScanStampURL,mapper.writeValueAsString(code));
            }
        } catch (JsonProcessingException e) {
        e.printStackTrace();
        }
    }
    public StampCard GetMyCardForID(String id) {
        for(StampCard card : mycards) {
            if(card.getId().equals(id)) {
                return card;
            }
        }
        return null;
    }
    public Company GetCompanyForID(String companyID) {
        for (Company company : availableCompanies) {
            if(company.getId().equals(companyID)) {
                return company;
            }
        }
        return null;
    }
    public Store GetStoreFromCompany(Company company, String storeID) {
         for (Store store : company.getStores()) {
             if(store.getId().equals(storeID)) {
                 return store;
             }
         }

        return null;
    }
    public Store GetStoreForID(String companyID, String storeID) {
        for (Company company : availableCompanies) {
            if(company.getId().equals(companyID)) {
                for (Store store : company.getStores()) {
                    if(store.getId().equals(storeID)) {
                        return store;
                    }
                }
            }
        }
        return null;
    }
    public void LoadMyStampCardsFromServer(final CardManagerCardUpdateCallback callback) {
        ObjectMapper jsonMapper = new ObjectMapper();
        WebserviceReturnObject result;
        //String json = "[{\"id\":\"ef946eebfbb24f84bff2086b3686f93f\",\"createdAt\":\"2016-05-11T09:28:58.842291+00:00\",\"updatedAt\":null,\"userId\":\"e805e74cbd164ce48a0a47ec4f8349eb\",\"companyId\":\"41337af111404588b33e6bbfe8933a49\",\"productName\":\"Coffee\",\"requiredStampCount\":10,\"bonusDescription\":\"Get one free coffee\",\"maxDuration\":365,\"isUsed\":false,\"currentStampCount\":0}]";

        new HttpGetJsonRequest(){
            @Override
            protected void onPostExecute(WebserviceReturnObject result) {
                if(result.getStatusCode() != Constants.HTTP_RESULT_OK) {
                    String tmp = result.getReturnString();
                }
                ObjectMapper jsonMapper = new ObjectMapper();
                try{
                    List<CardDTO> cards = mapper.readValue(result.getReturnString(),
                            TypeFactory.defaultInstance().constructCollectionType(List.class,
                                    CardDTO.class));
                    mycards.clear();
                    mycards.addAll(getStampCardList(cards));
                } catch(IOException exception) {
                   String s = exception.getStackTrace().toString();
                }
                if(callback != null) {
                    callback.CardsUpdated(mycards);
                }
            }
        }.execute(Constants.GetMyStampCardsURL);

    }
    private List<StampCard> getStampCardList(List<CardDTO> cards) {
        List<StampCard> newCards = new ArrayList<StampCard>();

        for(CardDTO cd : cards){
            StampCard card = new StampCard(cd.getId(),cd.getProductName(),cd.getCompanyId(),cd.getBonusDescription(),cd.getRequiredStampCount(),cd.getCurrentStampCount(),cd.getCreatedAt(),cd.getUpdatedAt(),cd.getMaxDuration(),cd.isUsed());
            newCards.add(card);
        }
        return newCards;
    }
    private List<Company> getCompanyList(List<CompanyDTO> companies) {
        List<Company> newCompanies = new ArrayList<Company>();
//(String id, String blobId,String companyName,String companyAddress,String contactName,String contactMail,String description,Date createdAt,Date updatedAt)

        for(CompanyDTO c : companies){
            Company company = new Company(c.getId(),c.getBlobId(),c.getCompanyName(),c.getCompanyAddress(),c.getContactName(),c.getContactMail(),c.getDescription(),c.getCreatedAt(),c.getUpdatedAt());
            newCompanies.add(company);
        }
        return newCompanies;
    }
    public void LoadProvidersFromServer() {
        Log.d("LoadProvider","begin of method");
        ObjectMapper jsonMapper = new ObjectMapper();
        WebserviceReturnObject result;
        try {

            new HttpGetJsonRequest(){
                @Override
                protected void onPostExecute(WebserviceReturnObject result) {
                    if(result.getStatusCode() != Constants.HTTP_RESULT_OK) {
                        String tmp = result.getReturnString();
                    }
                    Log.d("LoadProvider", "Count Status Code: " + result.getStatusCode());
                    ObjectMapper jsonMapper = new ObjectMapper();
                    try{
                        if(result.getReturnString() != null && result.getReturnString().length() > 0) {
                            CountDTO anz = jsonMapper.readValue(result.getReturnString(), CountDTO.class);
                            Log.d("CardManager", "ProviderCountRequest: " + anz.getCount());
                            new HttpGetJsonRequest() {
                                @Override
                                protected void onPostExecute(WebserviceReturnObject result) {
                                    if (result.getStatusCode() != Constants.HTTP_RESULT_OK) {
                                        String tmp = result.getReturnString();
                                    }
                                    ObjectMapper jsonMapper = new ObjectMapper();
                                    try {
                                        List<CompanyDTO> provider = (List<CompanyDTO>) jsonMapper.readValue(result.getReturnString(), CompanyDTO.class);
                                        availableCompanies.addAll(getCompanyList(provider));
                                        Log.d("CardManager", "First Company: " + availableCompanies.get(0));
                                        LoadCompanyBlobs();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.e("CardManager", "Abruf der Provider fehlgeschlagen", e);
                                    }
                                }

                            }.execute(Constants.GetStampItProvidersURL);
                        }
                    } catch(IOException exception) {
                        String s = exception.getStackTrace().toString();
                        Log.e("CardManager" ,s , exception);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("CardManager", e.getMessage() + " " + e.getStackTrace().toString());
                    }
                }
            }.execute(Constants.GetStampItProviderCountURL);

            /*
            result = WebService.getInstance().GetJSON(Constants.GetStampItProvidersURL);
            if(result.getStatusCode() != Constants.HTTP_RESULT_OK) {
                String tmp = result.getReturnString();
            }

            List<CompanyDTO> tmp = (List<CompanyDTO>)jsonMapper.readValue(result.getReturnString(), CompanyDTO.class);
            */


            //TODO Cast result to specific type and provide cards, where do i get card informations?
            //this.availableStampCards.clear();
            //this.availableStampCards.addAll(tmp);
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
    public void ResendUnverifiedTokes(final CardManagerStampUpdateCallback callback){
        //TODO Upload Stamps to Server
        callback.StampsUpdated();
    }
    private void LoadCompanyBlobs()
    {


    }
}
