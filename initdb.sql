create database web_chat;
use web_chat;

create table message(
	id int primary key AUTO_INCREMENT not null,
    conversation_id int,
    user_id int,
    `from` varchar(255),
    `to` varchar(255),
    content text,
    date_sent datetime,
    type varchar(255)
);

create table user(
	id int primary key AUTO_INCREMENT not null,
    username varchar(255),
    password varchar(255),
    isonline boolean,
    avatar varchar(256)
);

create table conversation(
	id int primary key auto_increment not null,
    group__name varchar(255),
    avatar varchar(255),
    creater varchar(255)
);

CREATE TABLE friends (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user1_id INT NOT NULL,
    user2_id INT NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES user(id),
    FOREIGN KEY (user2_id) REFERENCES user(id),
    CHECK (user1_id < user2_id) -- Đảm bảo không có mối quan hệ trùng lặp và không phân biệt thứ tự user1_id và user2_id
);

create table conversation_user(
	id int primary key auto_increment not null,
    conversation_id int,
    user_id int,
    foreign key (conversation_id) references conversation(id),
	foreign key (user_id) references user(id)
);

