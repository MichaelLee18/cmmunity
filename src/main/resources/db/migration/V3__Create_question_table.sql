create table question
(
	id int auto_increment,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modify bigint,
	creator int,
	comment_count int,
	reply_count int,
	like_count int,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);

