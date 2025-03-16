create table if not exists url_shorten_mapping(
  id int not null AUTO_INCREMENT,
  original_url varchar(100) not null,
  short_url varchar(15) not null,
  PRIMARY KEY ( id )
);