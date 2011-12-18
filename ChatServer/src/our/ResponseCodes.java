package our;

import java.io.Serializable;

public enum ResponseCodes implements Serializable
{
	UserAdded,	
	UserSignedIn,
	UserDeleted,
	UserLogOut,
	UserExists,
	UserUpdated,
	UserNotFound,
	
	RoomAdded,
	RoomDeleted,
	RoomUpdated,
	RoomExists,
	RoomNotFound,
	
	FriendAdded,
	FriendAcepted,
	FriendAborted, 
	FriendExists,
	FriendNotFound,
	
	BadRequest
}

