package com.jku.stampit.Services;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jku.stampit.StampItApplication;
import com.jku.stampit.data.StampCard;
import com.jku.stampit.data.Company;
import com.jku.stampit.data.Stamp;
import com.jku.stampit.data.Store;
import com.jku.stampit.dto.CardDTO;
import com.jku.stampit.dto.CompanyDTO;
import com.jku.stampit.dto.SessionTokenDTO;
import com.jku.stampit.utils.Constants;
import com.jku.stampit.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 04/05/16.
 * Class to retrieve and store all available cards for companies etc
 * this one should communicate with the backend server
 */
public class CardManager {
    private static CardManager instance;
    private final List<StampCard> mycards = new ArrayList<StampCard>();
    private final List<Company> availableCompanies = new ArrayList<Company>();
    //List for unverified Tokens, which must be sent to server
    //We dont know Syntax in Application for Security Reasons

    private final List<String> unverifiedStampTokens = new ArrayList<String>();

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
        mycards.addAll(getDummyCards());
        availableCompanies.addAll(getDummyCompanies());

        LoadStampCardsFromServer();
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

        Store st1 = new Store("asdf","ljöj","Altenbergerstraße 2/3",new Date(),new Date(),48.3366136,14.3171166);
        Store st2 = new Store("asdf1","ljöj1","Altenbergerstraße 14/3",new Date(),new Date(),48.3466136,14.3171166);
        Store st3 = new Store("asdf2","ljöj2","Altenbergerstraße 6/3",new Date(),new Date(),48.3366136,14.3271166);
        Store st4 = new Store("asdf3","ljöj3","Altenbergerstraße 10/1",new Date(),new Date(),48.4166136,14.3171166);
        Store st5 = new Store("asdf4","ljöj4","Altenbergerstraße 5/1",new Date(),new Date(),48.3666136,14.3271166);
        Store st6 = new Store("asdf5","ljöj5","Altenbergerstraße 8/3",new Date(),new Date(),48.3066136,14.3671166);
        Store st7 = new Store("asdf6","ljöj6","Altenbergerstraße 23/3",new Date(),new Date(),48.3366136,14.3171166);

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
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Yogurt",comp, "Ein gratis Yogurt",5,0,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Kebap",comp1, "Ein gratis Kebap",7,0,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"FrozenYogurt",comp, "Ein gratis Frozen Yogurt",10,0,new Date(), new Date(), 100000,false));
        stampCards.add(new StampCard(UUID.randomUUID().toString(),"Pizza",comp1, "Eine gratis Pizza",10,0,new Date(), new Date(), 100000,false));
        return stampCards;
    }
    /*
    public boolean addStampCard(StampCard stampCard)
    {
        for (StampCard c : mycards) {
            if(c.getId() == stampCard.getId() && !c.isFull()){
                return false;
            }
        }
        mycards.add(stampCard);
        return true;
    }
    */
    public boolean AddStamp(String qrCode){
        if(qrCode.isEmpty())
            return false;


        if(Utils.HasInternetConnection(StampItApplication.getContext())) {
            //TODO Send qr to Server
            WebService.WebServiceReturnObject ret = WebService.getInstance().PostString("",qrCode);

            //Check if connection was successfull
            if(ret.getStatusCode() == Constants.HTTP_RESULT_OK) {
                //TODO Request Card with ID or userid from Server
                //TODO Maybe add completionHandler to Update Current View
            }
            else {
                unverifiedStampTokens.add(qrCode);
            }
        }

        //only for test purposes
        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValueAsString(new CardDTO());

        return true;
    }
    public StampCard GetMyCardForID(String id) {
        for(StampCard card : mycards) {
            if(card.getId() == id) {
                return card;
            }
        }
        return null;
    }
    public Company GetCompanyForID(String companyID) {
        for (Company company : availableCompanies) {
            if(company.getId() == companyID) {
                return company;
            }
        }
        return null;
    }
    public Store GetStoreFromCompany(Company company, String storeID) {
         for (Store store : company.getStores()) {
             if(store.getId() == storeID) {
                 return store;
             }
         }

        return null;
    }
    public Store GetStoreForID(String companyID, String storeID) {
        for (Company company : availableCompanies) {
            if(company.getId() == companyID) {
                for (Store store : company.getStores()) {
                    if(store.getId() == storeID) {
                        return store;
                    }
                }
            }
        }
        return null;
    }
    public void LoadStampCardsFromServer() {
        ObjectMapper jsonMapper = new ObjectMapper();
        WebService.WebServiceReturnObject result;
        try {
            result = WebService.getInstance().GetJson(Constants.GetStampItProvidersURL);
            if(result.getStatusCode() != Constants.HTTP_RESULT_OK) {
                String tmp = result.getReturnDataString();
            }

            List<CompanyDTO> tmp = (List<CompanyDTO>)jsonMapper.readValue(result.getReturnDataString(), CompanyDTO.class);

            //TODO Cast result to specific type and provide cards, where do i get card informations?
            //this.availableStampCards.clear();
            //this.availableStampCards.addAll(tmp);
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
}
