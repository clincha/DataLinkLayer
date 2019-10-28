// MessageReceiver.java - PARTIAL IMPLEMENTATION

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the receiver side of the data link layer.
 * <p>
 * The source code supplied here only contains a partial implementation.
 * Your completed version must be submitted for assessment.
 * <p>
 * You only need to finish the implementation of the receiveMessage
 * method to complete this class.  No other parts of this file need to
 * be changed.  Do NOT alter the constructor or interface of any public
 * method.  Do NOT put this class inside a package.  You may add new
 * private methods, if you wish, but do NOT create any new classes.
 * Only this file will be processed when your work is marked.
 */

public class MessageReceiver {
  // Fields ----------------------------------------------------------

  private int mtu;                      // maximum transfer unit (frame length limit)
  private FrameReceiver physicalLayer;  // physical layer object
  private TerminalStream terminal;      // terminal stream manager

  // DO NOT ADD ANY MORE INSTANCE VARIABLES
  // but it's okay to define constants here

  private Pattern pattern = Pattern.compile("<([ED])-(\\d{2})-(.*)-(\\d{2})>");

  // Constructor -----------------------------------------------------

  /**
   * MessageReceiver constructor - DO NOT ALTER ANY PART OF THIS
   * Create and initialize new MessageReceiver.
   *
   * @param mtu the maximum transfer unit (MTU)
   *            (the length of a frame must not exceed the MTU)
   * @throws ProtocolException if error detected
   */

  public MessageReceiver(int mtu) throws ProtocolException {
    // Initialize fields
    // Create physical layer and terminal stream manager

    this.mtu = mtu;
    this.physicalLayer = new FrameReceiver();
    this.terminal = new TerminalStream("MessageReceiver");
    terminal.printlnDiag("data link layer ready (mtu = " + mtu + ")");
  }

  // Methods ---------------------------------------------------------

  /**
   * Receive a single message - THIS IS THE ONLY METHOD YOU NEED TO MODIFY
   *
   * @return the message received, or null if the end of the input
   * stream has been reached.  See receiveFrame documentation for
   * further explanation of how the end of the input stream is
   * detected and handled.
   * @throws ProtocolException immediately without attempting to
   *                           receive any further frames if any error is detected, such as
   *                           a corrupt frame, even if the end of the input stream has also
   *                           been reached (signalling an error takes precedence over
   *                           signalling the end of the input stream)
   */

  public String receiveMessage() throws ProtocolException {
    String message = "";    // whole of message as a single string
    // initialise to empty string

    // Report action to terminal
    // Note the terminal messages aren't part of the protocol,
    // they're just included to help with testing and debugging

    terminal.printlnDiag("  receiveMessage starting");

    // YOUR CODE SHOULD START HERE ---------------------------------
    // No changes are needed to the statements above


    // The following block of statements shows how the frame receiver
    // is invoked.  At the moment it just sets the message equal to
    // the first frame.  This is of course incorrect!  receiveMessage
    // should invoke receiveFrame separately for each frame of the
    // message in turn until the final frame in that message has been
    // obtained.  The message segments should be extracted and joined
    // together to recreate the original message string.  One whole
    // message should is processed by a single execution of receiveMessage
    // and returned as a single string.
    //
    // See the coursework specification and other class documentation
    // for further info.

    String frame = physicalLayer.receiveFrame();

    if (frame.equals(".")) return null;

    message = getFrameData(frame);

    // YOUR CODE SHOULD FINISH HERE --------------------------------
    // No changes are needed to the statements below

    // Return message

    if (message == null)
      terminal.printlnDiag("  receiveMessage returning null (end of input stream)");
    else
      terminal.printlnDiag("  receiveMessage returning \"" + message + "\"");
    return message;

  } // end of method receiveMessage

  // You may add private methods if you wish
  private String getFrameData(String frame) throws ProtocolException {
    //MTU
    if(frame.length() > mtu)
      throw new ProtocolException("Frame size exceeds MTU");

    Matcher matcher = pattern.matcher(frame);
    if (matcher.find()) {
      //Checksum
      String frameChecksum = frame.substring(frame.length() - 3, frame.length() - 1);
      String calculatedChecksum = String.format("%02d", frame.substring(1, frame.length() - 3).chars().sum() % 100);
      if (!frameChecksum.equals(calculatedChecksum))
        throw new ProtocolException("Invalid checksum");

      //Message Length
      String frameMessageLength = matcher.group(2);
      String calculatedMessageLength = String.format("%02d", matcher.group(3).length());
      if (!frameMessageLength.equals(calculatedMessageLength))
        throw new ProtocolException("Message length was incorrect");

      if (matcher.group(1).equals("E"))
        return matcher.group(3);
      if (matcher.group(1).equals("D"))
        return matcher.group(3) + getFrameData(physicalLayer.receiveFrame());
    }
    throw new ProtocolException("Malformed frame");
  }

} // end of class MessageReceiver

