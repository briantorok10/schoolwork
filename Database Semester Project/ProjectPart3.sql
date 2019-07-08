/*Drops all the previously named tables*/
DROP TABLE customer_location;
DROP TABLE sales_associate;
DROP TABLE item_location;
DROP TABLE customer;
DROP TABLE items;
DROP TABLE store_location;

/*Create the store table*/
CREATE TABLE store_location
(loc_id NUMBER(7),
street_address VARCHAR(50),
city VARCHAR(25),
state VARCHAR(2),
zip VARCHAR(5),
CONSTRAINT store_location_loc_id_pk PRIMARY KEY(loc_id)
);

/*Create the items table*/
CREATE TABLE items
(item_id NUMBER(12),
price NUMBER(8,2),
profit_margin NUMBER(8,2),
item_name VARCHAR(30),
CONSTRAINT item_item_id_pk PRIMARY KEY(item_id));

/*Create the customer table*/
CREATE TABLE customer
(cust_id NUMBER(10),
phone VARCHAR(10),
email VARCHAR(20),
zip NUMBER(5),
CONSTRAINT customer_cust_id_pk PRIMARY KEY(cust_id));

/*Create the item_location table*/
CREATE TABLE item_location
(item_id NUMBER(12),
loc_id NUMBER(7),
inventory NUMBER(5),
CONSTRAINT item_location_locid_itemid_pk PRIMARY KEY(loc_id,item_id),
CONSTRAINT item_location_loc_id_fk FOREIGN KEY(loc_id) REFERENCES store_location(loc_id),
CONSTRAINT item_id_item_id_fk FOREIGN KEY(item_id) REFERENCES items(item_id)
);

/*Create the sales_associate table*/
CREATE TABLE sales_associate
(employee_id NUMBER(5),
first_name VARCHAR(15),
last_name VARCHAR(15),
address VARCHAR(50),
city VARCHAR(25),
state VARCHAR(2),
zip NUMBER(5),
loc_id NUMBER(7),
CONSTRAINT sales_associte_employee_id_pk PRIMARY KEY(employee_id),
CONSTRAINT sales_associate_loc_id_fk FOREIGN KEY(loc_id) REFERENCES store_location(loc_id));

/*Create the customer_location table*/
CREATE TABLE customer_location
(cust_id NUMBER(10),
loc_id NUMBER(7),
CONSTRAINT cust_loc_cust_id_loc_id_pk PRIMARY KEY(cust_id, loc_id),
CONSTRAINT customer_location_cust_id_fk FOREIGN KEY(cust_id) REFERENCES customer(cust_id),
CONSTRAINT customer_location_loc_id_fk FOREIGN KEY(loc_id) REFERENCES store_location(loc_id)
);



INSERT INTO store_location VALUES
(1002111,'1234 Main St','Las Vegas','NV', 32154);
INSERT INTO store_location VALUES
(1002112,'4571 Marble Rd','New Brunswick','NJ',98745);
INSERT INTO store_location VALUES
(1002113,'546 Sever Rd','Lawrenceville','GA',30043);
INSERT INTO store_location VALUES
(1002114,'7410 Carnival Blvd','Miami','FL',10201);
INSERT INTO store_location VALUES
(1002115,'9630 Straddle Hwy','Houston','TX',65478);
INSERT INTO store_location VALUES
(1002116,'8520 Shoe Rd','Birmingham','AL',41078);
INSERT INTO store_location VALUES
(1002117,'654 Industrial Rd','Omaha','NA',45621);
INSERT INTO store_location VALUES
(1002118,'78945 Sugar Hill Dr','Chesapeake','VA',19467);


INSERT INTO items VALUES
(100023457011,550.00,100,'Gibson Les Paul Special');
INSERT INTO items VALUES
(100023457012,8.99,4.15,'Dunlop Picks');
INSERT INTO items VALUES
(100023457013,1599.99,300.99,'Fender Stratocaster');
INSERT INTO items VALUES
(100023457014,29.99,5.99,'XLR Cable');
INSERT INTO items VALUES
(100023457015,99.99,20,'Ibanez TS808 Tube Screamer');
INSERT INTO items VALUES
(100023457016,250.00,150,'Epiphone SG G-300');
INSERT INTO items VALUES
(100023457017,750.00,200.00,'Ibanez RG950');


INSERT INTO customer VALUES
(8004512740,'4046543217','crandall@verizon.net',30043);
INSERT INTO customer VALUES
(8004512741,'6786543210','jbearp@gmail.com',65412);
INSERT INTO customer VALUES
(8004512742,'7861201244','kdawson@mac.com',45122);
INSERT INTO customer VALUES
(8004512743,'7701212121','aprakash@comcast.net',78784);
INSERT INTO customer VALUES
(8004512744,'6787410520','brbarret@msn.com',74102);
INSERT INTO customer VALUES
(8004512745,'7410852012','dartlife@gmail.com',12365);
INSERT INTO customer VALUES
(8004512746,'7485961230','cmdrgravy@yahoo.ca',45689);
INSERT INTO customer VALUES
(8004512747,'6352419870','studyabr@yahoo.ca',12345);
INSERT INTO customer VALUES
(8004512748,'4515456230','nacho@msn.com',85201);
INSERT INTO customer VALUES
(8004512749,'1212478965','erynf@mac.com',12365);


INSERT INTO item_location VALUES
(100023457011,1002111,5);
INSERT INTO item_location VALUES
(100023457012,1002112,123);
INSERT INTO item_location VALUES
(100023457013,1002113,3);
INSERT INTO item_location VALUES
(100023457014,1002114,30);
INSERT INTO item_location VALUES
(100023457015,1002115,2);
INSERT INTO item_location VALUES
(100023457016,1002116,10);
INSERT INTO item_location VALUES
(100023457017,1002117,6);


INSERT INTO sales_associate VALUES
(10051,'John','Smith','3635 Cody Ridge Road','Guymon','OK',30124,1002111);
INSERT INTO sales_associate VALUES
(10052,'Alexander','Hmilton','4518 Masonic Hill Road','Little Rock','AK',72209,1002112);
INSERT INTO sales_associate VALUES
(10053,'Josh','Peck','2520 Hamilton Drive','Woodlawn','MD',21207,1002113);
INSERT INTO sales_associate VALUES
(10054,'Drake','Bell','1976 Hampton Meadows','Fitchburg','MA',65922,1002114);
INSERT INTO sales_associate VALUES
(10055,'Darth','Vader','66 Reactor Cir','Death Star','EN',38270,1002115);
INSERT INTO sales_associate VALUES
(10056,'Harry','Potter','2760 Rosewood Lane','New York','NY',10011,1002116);
INSERT INTO sales_associate VALUES
(10057,'Guy','Fieri','4866 Nutter Street','Saint Joseph','MO',64501,1002117);
INSERT INTO sales_associate VALUES
(10058,'Pika','Chu','2710 Oak Ridge Drive','Lewistown','MO',74102,1002118);


INSERT INTO customer_location VALUES
(8004512740,1002111);
INSERT INTO customer_location VALUES
(8004512741,1002112);
INSERT INTO customer_location VALUES
(8004512742,1002113);
INSERT INTO customer_location VALUES
(8004512743,1002114);
INSERT INTO customer_location VALUES
(8004512744,1002115);
INSERT INTO customer_location VALUES
(8004512745,1002116);
INSERT INTO customer_location VALUES
(8004512746,1002117);
INSERT INTO customer_location VALUES
(8004512747,1002118);
INSERT INTO customer_location VALUES
(8004512748,1002111);
INSERT INTO customer_location VALUES
(8004512749,1002112);
INSERT INTO customer_location VALUES
(8004512741,1002113);

commit;