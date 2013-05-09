package org.vertx.java.core.sockjs;

import java.util.Set;

import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

public interface EventBusBridgeHook {

  /**
   * The socket has been closed
   * @param sock The socket
   */
  void handleSocketClosed(SockJSSocket sock);

  /**
   * Client is sending or publishing on the socket
   * @param sock The sock
   * @param send if true it's a send else it's a publish
   * @param msg The message
   * @param address The address the message is being sent/published to
   * @return true To allow the send/publish to occur, false otherwise
   */
  boolean handleSendOrPub(SockJSSocket sock, boolean send, JsonObject msg, String address);

  /**
   * Called before client registers a handler
   * @param sock The socket
   * @param address The address
   * @return true to let the registration occur, false otherwise
   */
  boolean handlePreRegister(SockJSSocket sock, String address);

  /**
   * Called after client registers a handler
   * @param sock The socket
   * @param address The address
   */
  void handlePostRegister(SockJSSocket sock, String address);

  /**
   * Client is unregistering a handler
   * @param sock The socket
   * @param address The address
   */
  boolean handleUnregister(SockJSSocket sock, String address);

  /**
   * Called when a message is being sent to the client from the event bus  
   * @param authMetaData The meta data associated with the clients authentication  
   * @param address The address that the message is being sent to
   * @param msg The message
   * @return true if the message can be forwarded to the client
   */
  boolean applySendAuthRules(Set<JsonObject> authMetaData, String address, Message msg);
  
  /**
   * Called when a message is received from the client to be placed on the eventbus
   * @param message Message to be sent
   * @param authData meta data associated with the session that this message was sent with 
   * @return true if the message can be forwarded to the eventbus
   */
  boolean applyRecieveAuthRules(JsonObject message, JsonObject authData);


}
