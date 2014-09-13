package com.nilanc.herenow;

/**
 * Borrowed from QuickBlox github
 */
import android.util.Log;
import android.widget.Toast;

import com.quickblox.module.chat.QBChatRoom;
import com.quickblox.module.chat.QBChatService;
import com.quickblox.module.chat.listeners.ChatMessageListener;
import com.quickblox.module.chat.listeners.RoomListener;
import com.quickblox.module.chat.utils.QBChatUtils;
import com.quickblox.module.users.model.QBUser;
//import com.quickblox.sample.chat.App;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.util.Calendar;
import java.util.Date;

public class Room implements ChatInt, RoomListener, ChatMessageListener {

    public static final String EXTRA_ROOM_NAME = "name";
    public static final String EXTRA_ROOM_ACTION = "action";
    private static final String TAG = Room.class.getSimpleName();
    private Chat chat;
    private QBChatRoom chatRoom;

    public Room(Chat chat) {
        this.chat = chat;

        String chatRoomName = chat.getIntent().getStringExtra(EXTRA_ROOM_NAME);
        join( ((App) chat.getApplication()).getCurrentRoom());
    }

    @Override
    public void sendMessage(String message) throws XMPPException {
        if (chatRoom != null) {
            chatRoom.sendMessage(message);
        } else {
            Toast.makeText(chat, "Join unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void release() throws XMPPException {
        if (chatRoom != null) {
            QBChatService.getInstance().leaveRoom(chatRoom);
            chatRoom.removeMessageListener(this);
        }
    }

    @Override
    public void onCreatedRoom(QBChatRoom room) {
        Log.d(TAG, "room was created");
        chatRoom = room;
        chatRoom.addMessageListener(this);
    }

    @Override
    public void onJoinedRoom(QBChatRoom room) {
        Log.d(TAG, "joined to room");
        chatRoom = room;
        chatRoom.addMessageListener(this);
    }

    @Override
    public void onError(String msg) {
        Log.d(TAG, "error joining to room");
    }

    @Override
    public void processMessage(Message message) {
        Date time = QBChatUtils.parseTime(message);
        if (time == null) {
            time = Calendar.getInstance().getTime();
        }
        // Show message
        QBUser qbUser = ((App) (chat.getApplication())).getQbUser();
        chat.showMessage(new com.nilanc.herenow.Message(message.getBody(), new Date()));
    }

    @Override
    public boolean accept(Message.Type messageType) {
        switch (messageType) {
            case groupchat:
                return true;
            default:
                return false;
        }
    }

    public void create(String roomName) {
        // Creates open & persistent room
        QBChatService.getInstance().createRoom(roomName, false, true, this);
    }

    public void join(QBChatRoom room) {
        QBChatService.getInstance().joinRoom(room, this);
    }

    public static enum RoomAction {CREATE, JOIN}
}