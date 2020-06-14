create table comment
(
	id int auto_increment,
	parent_id int not null,
	type int not null,
	commentator int not null,
	gmt_create bigint not null,
	gmt_modify bigint not null,
	like_count int default 0 not null,
	constraint comment_pk
		primary key (id)
);

