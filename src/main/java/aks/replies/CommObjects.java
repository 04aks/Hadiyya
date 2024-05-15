package aks.replies;

import java.util.List;

public class CommObjects {


    public R_CCP rCcp = new R_CCP();
    public R_email rEmail = new R_email();
    public R_Confirm rConfirm = new R_Confirm();

    List<String> list = List.of("/order", "/email", "/ccp");

    public CommObjects(){
        System.out.println(list);
    }

    public Commands returnObject(String string){
        if(list.contains(string)){
            System.out.println("fk yea");
        }
        switch(string){
            case "/email": return rEmail;
            case "/ccp": return rCcp;
            // case "/confirm": return rConfirm;
        }
        return null;
    }

    
}
