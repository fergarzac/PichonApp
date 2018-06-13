package fernando.pichonapp.com.pichonapp;

/**
 * Created by FernandoG on 30/05/2018.
 */

public class Usuario {
    private String idu,email,nickname;
    public Usuario(String idu, String email,String nickname){
        this.idu=idu;
        this.email=email;
        this.nickname=nickname;
    }
    public String getIdu(){
        return idu;
    }

    public void setIdu(String idu){
        this.idu=idu;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
}
