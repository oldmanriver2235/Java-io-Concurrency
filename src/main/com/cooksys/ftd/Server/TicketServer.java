package com.cooksys.ftd.ticker.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TickerServer
{
  public static void main(String[] args)
  {
    try
    {
      ServerSocket server = new ServerSocket(4000);
      for (;;)
      {
        new Thread(new WorkInProgress(server.accept())).start();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}