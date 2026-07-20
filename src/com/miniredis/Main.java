package com.miniredis;

import com.miniredis.store.Store;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       Store store = new Store();
       Scanner scanner = new Scanner(System.in);
       final String STATUS_SUCCESS = "(integer) 1";
       final String STATUS_FAILURE = "(integer) 0";


       System.out.println("Mini-Redis, type commands (QUIT to exit)");

       while(scanner.hasNextLine()) {
           String line = scanner.nextLine();

           if(line.isEmpty()) continue;

           String[] parts = line.split("\\s+");
           String command = parts[0].toUpperCase();

           try {
               switch(command) {
                   case "SET" -> {
                       store.set(parts[1], parts[2]);
                       System.out.println("OK");
                   }
                   case "GET" -> {
                       System.out.println(store.get(parts[1]).map(v -> "\"" + v + "\"").orElse("(nil)"));
                   }
                   case "DEL" -> {
                       boolean deleted = store.delete(parts[1]);
                       if(deleted)
                       System.out.println(STATUS_SUCCESS);
                       else System.out.println(STATUS_FAILURE);
                   }
                   case "EXISTS" -> {
                       boolean exists = store.exists(parts[1]);
                       if(exists)
                           System.out.println(STATUS_SUCCESS);
                       else System.out.println(STATUS_FAILURE);
                   }
                   case "QUIT" -> {
                       System.out.println("Bye !");
                       return;
                   }
                   default -> System.out.printf("(error) ERR unknown command '%s'%n", command.toLowerCase());
               }
           } catch (ArrayIndexOutOfBoundsException e) {
               System.out.printf("(error) ERR wrong number of arguments for '%s' command%n", command);
           } catch (Exception e) {
               System.out.println("ERR unknown exception: " + e.getMessage());
           }
       }
    }
}