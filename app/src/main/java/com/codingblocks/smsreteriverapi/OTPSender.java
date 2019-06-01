package com.codingblocks.smsreteriverapi;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class OTPSender {
    String response;
    URL url;
    URLConnection myURLConnection=null;
    URL myURL=null;
    BufferedReader reader=null;
    private String authKey = "236211ALWfvcwYmC35b9242d8";
    private String phoneNumber;
    //private String message = "Your%20OTP%20is%20##OTP##";
    private String message ="<#>%20YourExampleAppcodeis:%20##OTP##%20fI6GhsYpicY";
    private String sender = "EAZYPG";
    private String otpLength = "6";
    private String otpExpiry = "5";

    public OTPSender(String phoneNumber){
        this.phoneNumber = "+91"+phoneNumber;
    }

    public void setOTPLength(String otpLength){
        this.otpLength = otpLength;
    }

    public void setOTPMessage(String message){
        this.message = message;
    }

    public void setAuthKey(String authKey){
        this.authKey = authKey;
    }

    public void sendOTP(){
        if(!phoneNumber.isEmpty()){

            try
            {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {

                            URL url = new URL("http://control.msg91.com/api/sendotp.php?");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000);
                            conn.setConnectTimeout(15000);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            Uri.Builder builder = new Uri.Builder()
                                    .appendQueryParameter("authkey", authKey)
                                    .appendQueryParameter("mobile", phoneNumber)
                                    .appendQueryParameter("message", message)
                                    .appendQueryParameter("sender", sender)
                                    .appendQueryParameter("otp_expiry", otpExpiry)
                                    .appendQueryParameter("otp_length", otpLength);

                            String query = builder.build().getEncodedQuery();

                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(os, "UTF-8"));
                            writer.write(query);
                            writer.flush();
                            writer.close();
                            os.close();

                            conn.connect();

                            int responseCode=conn.getResponseCode();

                            if (responseCode == HttpsURLConnection.HTTP_OK) {
                                String line;
                                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                while ((line=br.readLine()) != null) {
                                    response+=line;
                                }
                            }
                            else {
                                response="";
                            }
                            Log.e("response",response);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean verifyOTP(final String otp){
        try
        {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        URL url = new URL("http://control.msg91.com/api/verifyRequestOTP.php?");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("authkey", authKey)
                                .appendQueryParameter("mobile", phoneNumber)
                                .appendQueryParameter("otp", otp);

                        String query = builder.build().getEncodedQuery();

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();

                        conn.connect();

                        int responseCode=conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            response="";
                        }
                        Log.e("response",response);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

            String s = response.substring(response.lastIndexOf(':') + 1);
            s = s.substring(1, s.length() - 2);
            if(s.equals("success")){
                return true;
            }
            else
                return false;


    }

}
