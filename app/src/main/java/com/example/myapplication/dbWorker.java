package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class dbWorker extends AsyncTask {
    private Context myC;
    Integer login_register = 0;



    public dbWorker(Context c) {
        this.myC = c;
    }


    @Override
    protected void onPreExecute() {


    }


    private void showAlert(String title, String msg) {
        AlertDialog al = new AlertDialog.Builder(myC).create();
        al.setTitle(title);
        al.setMessage(msg);
        al.create();
        al.show();
    }

    @Override
    protected String doInBackground(Object[] param) {
        String cible = new String();
        switch ((String) param[0]) {
            case "login":
                cible = "http://192.168.56.1/login.php";
                break;
            case "register":
                cible = "http://192.168.56.1/register.php";
                break;
            case "updateLocation":
                cible = "http://192.168.56.1/updateLocation.php";
                break;
            case "updateAutoTrue":
                cible = "http://192.168.56.1/updateAutoTrue.php";
                break;
            case "updateAutoFalse":
                cible = "http://192.168.56.1/updateAutoFalse.php";
                break;
            case "userSharedLocation":
                cible = "http://192.168.56.1/userSharedLocation.php";
                break;
            case "updateProfileNoPw":
                cible = "http://192.168.56.1/updateProfileNoPw.php";
                break;
            case "updateProfileWPw":
                cible = "http://192.168.56.1/updateProfileWPw.php";
                break;
            case "selectLocUser":
                cible = "http://192.168.56.1/selectLocUser.php";
                break;
        }

        try {
            URL url = new URL(cible);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            OutputStream outs = con.getOutputStream();
            BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs, "utf8"));

            String msg = new String();

            switch ((String) param[0]) {
                case "login":
                    msg = URLEncoder.encode("user", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8") +
                            "&" + URLEncoder.encode("pw", "utf-8") + "=" +
                            URLEncoder.encode((String) param[2], "utf8");
                    login_register = 1;
                    break;
                case "register":
                    msg = URLEncoder.encode("name", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8") +
                            "&" + URLEncoder.encode("user", "utf-8") + "=" +
                            URLEncoder.encode((String) param[2], "utf8") +
                            "&" + URLEncoder.encode("pw", "utf-8") + "=" +
                            URLEncoder.encode((String) param[3], "utf8") +
                            "&" + URLEncoder.encode("phone", "utf-8") + "=" +
                            URLEncoder.encode((String) param[4], "utf8");

                    login_register = 2;
                    break;
                case "updateLocation":
                    msg = URLEncoder.encode("location", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8") +
                            "&" + URLEncoder.encode("id", "utf-8") + "=" +
                            URLEncoder.encode((String) param[2], "utf8");
                    login_register = 3;
                    break;
                case "updateAutoTrue":
                    msg = URLEncoder.encode("id", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8");
                    login_register = 4;
                    break;
                case "updateAutoFalse":
                    msg = URLEncoder.encode("id", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8");
                    login_register = 5;
                    break;
                case "userSharedLocation":
                    login_register = 6;
                    break;
                case "updateProfileNoPw":
                    msg= URLEncoder.encode("id", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8") +
                            "&" + URLEncoder.encode("pw", "utf-8") + "=" +
                            URLEncoder.encode((String) param[2], "utf8") +
                            "&" + URLEncoder.encode("user", "utf-8") + "=" +
                            URLEncoder.encode((String) param[3], "utf8") +
                            "&" + URLEncoder.encode("name", "utf-8") + "=" +
                            URLEncoder.encode((String) param[4], "utf8") +
                            "&" + URLEncoder.encode("phone", "utf-8") + "=" +
                            URLEncoder.encode((String) param[5], "utf8");

                    login_register = 7;
                    break;
                case "updateProfileWPw":
                    msg= URLEncoder.encode("id", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8") +
                            "&" + URLEncoder.encode("pw", "utf-8") + "=" +
                            URLEncoder.encode((String) param[2], "utf8") +
                            "&" + URLEncoder.encode("user", "utf-8") + "=" +
                            URLEncoder.encode((String) param[3], "utf8") +
                            "&" + URLEncoder.encode("name", "utf-8") + "=" +
                            URLEncoder.encode((String) param[4], "utf8") +
                            "&" + URLEncoder.encode("phone", "utf-8") + "=" +
                            URLEncoder.encode((String) param[5], "utf8")+
                            "&" + URLEncoder.encode("npw", "utf-8") + "=" +
                            URLEncoder.encode((String) param[6], "utf8");
                    login_register = 8;
                    break;
                case "selectLocUser":
                    msg= URLEncoder.encode("user", "utf-8") + "=" +
                            URLEncoder.encode((String) param[1], "utf8");
                    login_register = 9;
                    break;
            }

            bufw.write(msg);
            bufw.flush();
            bufw.close();
            outs.close();


            //recup======================================
            InputStream ins = con.getInputStream();

            BufferedReader bufr = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));
            String line;
            StringBuffer sbuff = new StringBuffer();

            while ((line = bufr.readLine()) != null) {
                sbuff.append(line + "\n");

            }
            return sbuff.toString();


        } catch (Exception e) {
            showAlert("Warning", e.getMessage());

            Log.i("toto", e.getMessage());
            return e.getMessage();
        }


    }


    //POST EXECUTE ===================================================================================================================================


    @Override
    protected void onPostExecute(Object o) {

        String o_str = o.toString();


        //error Invalid Credential ==================================================
        if (o_str.contains("{'Error':false")) {
            int errorFmsgI = "{'Error':false,'Message':'Invalid credentials'".length() - 15;
            String errCredential = o_str.substring(errorFmsgI);
            if (errCredential.length() > 0) {
                o_str = o_str.substring(0, errorFmsgI);
            }
        }
        //=========================================================================


        //in login => params[0] = "login"
        if (login_register == 1) {
            //STATUS response from PHP backend ============================
            if (o_str.contains("status200")) {
                //STATUS OK
                String[] substrings = o_str.split("!!");

                for (int i = 0; i < substrings.length; i++) {
                    switch (i) {
                        case 1:
                            StaticLog.id = substrings[i];
                            break;
                        case 2:
                            StaticLog.name = substrings[i];
                            break;
                        case 3:
                            StaticLog.user = substrings[i];
                            break;
                        case 4:
                            StaticLog.localisation = substrings[i]+"!!"+substrings[i++];
                            break;
                        case 6:
                            StaticLog.phone = substrings[i];
                            break;
                        case 7:
                            if (substrings[i] == "true") {
                                StaticLog.auto = "true";
                            } else {
                                StaticLog.auto = "false";
                            }
                            break;
                    }
                }

                //static var then Rediect =================================
                Intent intent = new Intent(myC, MainActivity_Logedin.class);
                myC.startActivity(intent);

            } else if (o_str.contains("status500")) {
                //ERROR in php code
                showAlert("Warning", "Oops! Something went wrong!");
            } else if (o_str.contains("status404")) {
                //not found :/
                showAlert("Warning", "Username or password is Wrong!");
            }
        } else if (login_register == 2) {
            if (o_str.contains("status200")) {
                //STATUS OK 200
                try {

                    //Message then Redirect ===============================================================
                    showAlert("Warning", "Sign Up with Succes, You will be redirected to the Login page soon!");

                    TimeUnit.SECONDS.sleep(7);
                    Thread.sleep(7000);
                    Intent intent = new Intent(myC, MainActivity.class);
                    myC.startActivity(intent);

                } catch (Exception e) {

                }


            } else if (o_str.contains("status500")) {
                //ERROR in php code
                showAlert("Warning", "Oops! Something went wrong!");
            } else if (o_str.contains("status404")) {
                //not found :/
                showAlert("Warning", "Oops! Something went wrong!");
            } else if (o_str.contains("lol")) {
                showAlert("Warning", "Username already taken! try another one.");

            }
        } else if (login_register == 3) {
            if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");
            } else if (o_str.contains("status200")) {
                showAlert("Update", "Location updated with SUCCES!");
                Log.i("tag1", "YES : " + o_str);
            }
            else if (o_str.contains("status404")) {
                showAlert("Update", "Cannot updated your Location!");
            }

        } else if (login_register == 4) {
            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't share your location!");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else {

                showAlert("Update", "The share of your location has been updated!" +
                        "\nOther users can see you in the list.");
                StaticLog.auto = "true";
            }

        }else if (login_register == 5) {
            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't share your location!");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else {

                showAlert("Update", "The share of your location has been updated!" +
                        "\nOther users cannot see you in the list.");
                StaticLog.auto = "false";
            }

        }
        else if (login_register == 6) {
            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't share your location!");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else if (o_str.contains("status200")) {

                try{

                    String[] substring = o_str.split("@");

                    for (int j = 0; j < substring.length-1; j++){

                        if(!substring[j].contains("\n")){
                            // Split each substring using the "!!" delimiter
                            String[] substrings = substring[j].split("!!");
                            // Create a new ObjetUser object
                            ObjetUser ou = new ObjetUser();
                            // Set the object's properties using the substrings
                            if (substrings[2] != null) {
                                ou.name = substrings[2];
                            } else {
                                ou.name = "private";
                            }
                            if (substrings[3] != null) {
                                ou.username = substrings[3];
                            } else {
                                ou.username = "private";
                            }
                            if (substrings[4] != null) {

                                //init
                                Geocoder geocoder = new Geocoder(myC, Locale.getDefault());
                                //get
                                List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(substrings[4]) , Double.parseDouble(substrings[5]), 1);
                                Address address = addresses.get(0);

                                ou.localisation = address.getLocality().toUpperCase() + ", " +
                                        address.getCountryName().toUpperCase();
                            } else {
                                ou.localisation = "private";
                            }
                            if (substrings[6] != null) {
                                ou.phone = substrings[6];
                            } else {
                                ou.phone = "private";
                            }

                            // Add the object to the list
                            MainActivity_Logedin.lst.add(ou);

                        }
                    }

//                //LIST VIEW
                    try{
                        MainActivity_Logedin.myArrayAdapter = new myArrayAdapter(myC, R.layout.simple_lstv, MainActivity_Logedin.lst);
                        MainActivity_Logedin.lstView.setAdapter(MainActivity_Logedin.myArrayAdapter);

                        if (MainActivity_Logedin.myArrayAdapter != null) {
                            MainActivity_Logedin.myArrayAdapter.notifyDataSetChanged();
                        }



                    }
                    catch (Exception ex){
                        Log.i("tutu", ex.getMessage());
                    }


                }
                catch(Exception e){
                    Log.i("titi", "onPostExecute: " + e.getMessage());
                }


            }


        }

        else if (login_register == 7) {
            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't update your profile!\n" +
                        "Your password is not correct! try again.");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else if(o_str.contains("status")){

                showAlert("Update", "Your profile has been updated" + o_str);

            }

        }
        else if (login_register == 8) {
            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't update your profile!\n" +
                        "Your password is not correct! try again.");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else {

                showAlert("Update", "Your profile has been updated");

            }

        }
        else if (login_register == 9) {

            if (o_str.contains("status404")) {
                showAlert("Warning", "Oops! Couldn't update your profile!\n" +
                        "Your password is not correct! try again.");

            } else if (o_str.contains("status500")) {
                showAlert("Warning", "Oops! Something went wrong!");

            } else {

                try {

                    String[] substring = o_str.split("/n");

                    for (int j = 0; j < substring.length; j++) {

                        if (!substring[j].contains("\n")) {

                            String[] substrings = substring[j].split("!!");

                            if (substrings[3] != null){
                                MainActivity_Gmap.userusername = substrings[3];
                            }
                            if (substrings[6] != null){
                                MainActivity_Gmap.userphone = substrings[6];
                            }

                            if (substrings[4] != null) {

                                showAlert("Warning", "Usermap" + o_str);
                                MainActivity_Gmap.mapIndex = 2;

                                MainActivity_Gmap.userloc = new LatLng(Double.parseDouble(substrings[4]), Double.parseDouble(substrings[5]));
                                Intent i = new Intent(myC, MainActivity_Gmap.class);
                                myC.startActivity(i);


                            }



                        }
                    }//end for



                }catch (Exception e){
                    Log.i("tagg", e.getMessage());
                }



            }

        }
    }
}







