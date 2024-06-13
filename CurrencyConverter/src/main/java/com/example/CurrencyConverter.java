package com.example;

import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class CurrencyConverter {
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    int option = 0;
	    
	    do {
	        System.out.println("************************************************************");
	        System.out.println("\nSea bienvenido/a al conversor de monedas =]");
	        System.out.println("1) Dólar =>> Peso argentino");
	        System.out.println("2) Peso argentino =>> Dólar");
	        System.out.println("3) Dólar =>> Real brasileño");
	        System.out.println("4) Real brasileño =>> Dólar");
	        System.out.println("5) Dólar =>> Peso colombiano");
	        System.out.println("6) Peso colombiano =>> Dólar");
	        System.out.println("7) Salir");
	        
	        System.out.print("\nDigite una opción valida: ");
	        option = sc.nextInt();
	        
	        if (0 < option && option < 7) {
    	        String currentName = option % 2 == 1 ? "USD" : option == 2 ? "ARS" : option == 4 ? "BRL" : "COP";
    	        String convertedName = option % 2 == 0 ? "USD" : option == 1 ? "ARS" : option == 3 ? "BRL" : "COP";
    	       
    	        System.out.print("Digite el valor a convertir: ");
    	        double currentValue = sc.nextDouble();
    	        double convertedValue = convertValue(currentName, convertedName, currentValue);
    	       
    	        String message = "El valor " + currentValue + " (" + currentName + ") corresponde al valor final de =>>> " + convertedValue + " (" + convertedName + ")";
    	        System.out.println(message);
	        }
	    } while (option != 7);
	}
	
	private static double getExchangeRate(String currentCurrency, String targetCurrency) {
        try {
            String API_KEY = "be086c01da951d4099c89a5e";
            String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + currentCurrency;
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject json = new JSONObject(content.toString());
            return json.getJSONObject("conversion_rates").getDouble(targetCurrency);
        } catch (Exception e) {
            return 0;
        }
    }

    private static double convertValue(String currentCurrency, String targetCurrency, double value) {
        return getExchangeRate(currentCurrency, targetCurrency) * value;
    }
}
