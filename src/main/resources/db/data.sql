insert into user_tb(username, password, email, created_at) values('ssar', '1234', 'ssar@nate.com', now());
insert into user_tb(username, password, email, created_at) values('love', '1234', 'love@nate.com', now());


insert into board_tb(title, content, thumbnail, user_id, created_at) values('첫 번째 제목입니다.','첫 번째 내용입니다.','/images/dora.png', 1, now()); 
insert into board_tb(title, content, thumbnail, user_id, created_at) values('두 번째 제목입니다.','두 번째 내용입니다.','/images/dora.png' ,1, now()); 
insert into board_tb(title, content, thumbnail, user_id, created_at) values('세 번째 제목입니다.','셋 번째 내용입니다.','/images/dora.png' ,1, now()); 
insert into board_tb(title, content, thumbnail, user_id, created_at) values('네 번째 제목입니다.','넷 번째 내용입니다.','/images/dora.png' ,2, now()); 
insert into board_tb(title, content, thumbnail, user_id, created_at) values('다섯 번째 제목입니다.','다섯 번째 내용입니다.','/images/dora.png', 2, now()); 
insert into board_tb(title, content, thumbnail, user_id, created_at) values('여섯 번째 제목입니다.','여섯 번째 내용입니다.','/images/dora.png', 2, now()); 

insert into reply_tb(comment, user_id, board_id, created_at) values('첫 번째 댓글',1, 1, now()) ;
insert into reply_tb(comment, user_id, board_id, created_at) values('두 번째 댓글',1, 2, now()) ;
insert into reply_tb(comment, user_id, board_id, created_at) values('세 번째 댓글',2, 1, now()) ;
insert into reply_tb(comment, user_id, board_id, created_at) values('네 번째 댓글',2, 2, now()) ;
commit;
 
