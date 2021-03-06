// MessageSender.java - PARTIAL IMPLEMENTATION

/**
 * This class implements the sender side of the data link layer.
 * <p>
 * The source code supplied here only contains a partial implementation.
 * Your completed version must be submitted for assessment.
 * <p>
 * You only need to finish the implementation of the sendMessage
 * method to complete this class.  No other parts of this file need to
 * be changed.  Do NOT alter the constructor or interface of any public
 * method.  Do NOT put this class inside a package.  You may add new
 * private methods, if you wish, but do NOT create any new classes.
 * Only this file will be processed when your work is marked.
 */

public class MessageSender {
  // Fields ----------------------------------------------------------

  private int FRAME_METADATA_SIZE = 10;
  private int mtu;                    // maximum transfer unit (frame length limit)
  private FrameSender physicalLayer;  // physical layer object

  // DO NOT ADD ANY MORE INSTANCE VARIABLES
  // but it's okay to define constants here
  private TerminalStream terminal;    // terminal stream manager

  // Constructor -----------------------------------------------------

  /**
   * MessageSender constructor - DO NOT ALTER ANY PART OF THIS
   * Create and initialize new MessageSender.
   *
   * @param mtu the maximum transfer unit (MTU)
   *            (the length of a frame must not exceed the MTU)
   * @throws ProtocolException if error detected
   */

  public MessageSender(int mtu) throws ProtocolException {
    // Initialize fields
    // Create physical layer and terminal stream manager

    this.mtu = mtu;
    this.physicalLayer = new FrameSender();
    this.terminal = new TerminalStream("MessageSender");
    terminal.printlnDiag("data link layer ready (mtu = " + mtu + ")");
  }

  // Methods ---------------------------------------------------------

  /**
   * Send a single message - THIS IS THE ONLY METHOD YOU NEED TO MODIFY
   *
   * @param message the message to be sent.  The message can be any
   *                length and may be empty but the string reference should not
   *                be null.
   * @throws ProtocolException immediately without attempting to
   *                           send any further frames if, and only if, the physical layer
   *                           throws an exception or the given message can't be sent
   *                           without breaking the rules of the protocol (including the MTU)
   */

  public void sendMessage(String message) throws ProtocolException {
    // Report action to terminal
    // Note the terminal messages aren't part of the protocol,
    // they're just included to help with testing and debugging

    terminal.printlnDiag("  sendMessage starting (message = \"" + message + "\")");

    // YOUR CODE SHOULD START HERE ---------------------------------
    // No changes are needed to the statements above

    int messageLength = mtu - FRAME_METADATA_SIZE;

    if (messageLength <= 0) throw new ProtocolException("MTU is too small for frame");
    if (messageLength >= 100) messageLength = 99;

    int messageSegmentsCount = ((int) Math.ceil(((float) message.length()) / ((float) messageLength)));

    // Bring message segments count up to 1 if there is a blank message
    messageSegmentsCount = messageSegmentsCount == 0 ? 1 : messageSegmentsCount;

    String[] messageSegments = new String[messageSegmentsCount];

    for (int i = 0; i < messageSegmentsCount; i++) {
      int substringStart = messageLength * i;
      try {
        messageSegments[i] = message.substring(substringStart, substringStart + messageLength);
      } catch (StringIndexOutOfBoundsException e) {
        messageSegments[i] = message.substring(substringStart);
      }
    }

    for (int i = 0; i < messageSegmentsCount; i++) {
      String messageSegment = i == (messageSegmentsCount - 1) ? "E-" : "D-";
      messageLength = messageSegments[i].length();
      messageSegment = messageSegment + String.format("%02d", messageLength) + "-" + messageSegments[i] + "-";
      String checkSum = String.format("%02d", messageSegment.chars().sum() % 100);
      messageSegments[i] = "<" + messageSegment + checkSum.substring(checkSum.length() - 2) + ">";
    }


    // The following statement shows how the frame sender is invoked.
    // At the moment it just passes a fixed string.
    // sendMessage should split large messages into several smaller
    // segments.  Each segment must be encoded as a frame in the
    // format specified.  sendFrame will need to be called separately
    // for each frame in turn.  See the coursework specification
    // and other class documentation for further info.

    for (String messageSegment : messageSegments) {
      physicalLayer.sendFrame(messageSegment);
    }

    // YOUR CODE SHOULD FINISH HERE --------------------------------
    // No changes are needed to the statements below

    // Report completion of task

    terminal.printlnDiag("  sendMessage finished");

  } // end of method sendMessage

  // You may add private methods if you wish


} // end of class MessageSender

