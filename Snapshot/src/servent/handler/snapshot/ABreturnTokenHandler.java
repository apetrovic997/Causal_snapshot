package servent.handler.snapshot;

import app.AppConfig;
import app.CausalBroadcastShared;
import app.ServentInfo;
import servent.handler.MessageHandler;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.util.MessageUtil;

public class ABreturnTokenHandler implements MessageHandler{
	
	private Message clientMessage;
//	private SnapshotCollector snapshotCollector;
	
	public ABreturnTokenHandler(Message clientMessage)
	{
	
		this.clientMessage = clientMessage;
//		this.snapshotCollector = snapshotCollector;
		
	}

	@Override
	public void run() 
	{
		
		if(clientMessage.getMessageType() == MessageType.AB_RETURN_TOKEN)
		{
			ServentInfo senderInfo = clientMessage.getOriginalSenderInfo();
			if(senderInfo.getId() == AppConfig.myServentInfo.getId())
			{
				AppConfig.timestampedStandardPrint("Got my own message no rebroadcast.");
			}
			else
			{
				boolean didPut = AppConfig.getReceivedBroadcasts().add(clientMessage);
				
				if(didPut)
				{
					CausalBroadcastShared.addPendingMessage(clientMessage);
					CausalBroadcastShared.checkPendingMessages();
					
//					ABtokenMessage abTokenMessage = (ABtokenMessage)clientMessage;
					
					for (Integer neighbor : AppConfig.myServentInfo.getNeighbors()) 
					{
						MessageUtil.sendMessage(clientMessage.changeReceiver(neighbor));
					}
				}
				
			}
		}
		else
		{
			AppConfig.timestampedErrorPrint("ABtoken handler got: " + clientMessage);
		}
		
		
		
	}

}
