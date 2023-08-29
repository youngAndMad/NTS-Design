// chat-service: [ws-connection, send message, redirect new messages to kafka, subscribe redis streams]

//===================================================================================================
// chat-redis-service: [consume kafka: new_message_topic => save to redis, publish to redis streams]
// scheduled => { filter => message{ [currentTime - message.time] >= some_period } => then => kafka[some_topic] }


//===================================================================================================

// chat-cassandra-service: [consume kafka: some_topic => save to db]


// user : 1    2   3
//        db redis db

// channel_private_chat

// channel_room_chat

// chat.id , [messages:{id,content:string,time:LocalDateTime,ownerID:integer,users:[{id:1,name:string,surname:string,position:string}]}]
// user: chat.id

// chat counter = 1
// message.innerId = counter
// counter++
