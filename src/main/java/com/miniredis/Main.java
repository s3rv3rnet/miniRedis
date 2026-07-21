package com.miniredis;

import com.miniredis.command.*;
import com.miniredis.store.Store;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       Store store = new Store();
       CommandExecutor commandExecutor = new CommandExecutor(store);

       Scanner scanner = new Scanner(System.in);
       System.out.println("Mini-Redis, type commands (QUIT to exit)");

       while(scanner.hasNextLine()) {
           String line = scanner.nextLine();

           if(line.isEmpty()) continue;

           try {
               Command command = CommandParser.parse(line);
               Result result = commandExecutor.execute(command);
               if(result instanceof Result.Quit) {
                   System.out.println("Bye !");
                   return;
               }
               System.out.println(result.toReplyString());
           } catch (CommandException e) {
               System.out.println(new Result.Error(e.getMessage()).toReplyString());
           }
       }
    }
}