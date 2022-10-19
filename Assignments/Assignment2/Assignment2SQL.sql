BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Customers" (
	"CustomerID"	INTEGER NOT NULL UNIQUE,
	"CustomerName"	TEXT NOT NULL,
	"ContactName"	TEXT NOT NULL,
	"Address"	TEXT NOT NULL,
	"City"	TEXT NOT NULL,
	"PostalCode"	TEXT NOT NULL,
	"Country"	TEXT NOT NULL,
	PRIMARY KEY("CustomerID")
);
CREATE TABLE IF NOT EXISTS "Categories" (
	"CategoryID"	INTEGER NOT NULL UNIQUE,
	"CategoryName"	TEXT NOT NULL UNIQUE,
	"Description"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("CategoryID")
);
CREATE TABLE IF NOT EXISTS "Order Details" (
	"OrderDetailID"	INTEGER NOT NULL UNIQUE,
	"OrderID"	INTEGER NOT NULL,
	"ProductID"	INTEGER NOT NULL,
	"Quantity"	INTEGER NOT NULL,
	PRIMARY KEY("OrderDetailID")
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"OrderID"	INTEGER NOT NULL UNIQUE,
	"CustomerID"	INTEGER NOT NULL,
	"EmployeeID"	INTEGER NOT NULL,
	"OrderDate"	TEXT NOT NULL,
	"ShipperID"	INTEGER NOT NULL,
	PRIMARY KEY("OrderID")
);
CREATE TABLE IF NOT EXISTS "Shippers" (
	"ShipperID"	INTEGER NOT NULL UNIQUE,
	"ShipperName"	TEXT NOT NULL UNIQUE,
	"Phone"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("ShipperID")
);
CREATE TABLE IF NOT EXISTS "Suppliers" (
	"SupplierID"	INTEGER NOT NULL UNIQUE,
	"SupplierName"	TEXT NOT NULL UNIQUE,
	"ContactName"	TEXT NOT NULL,
	"Address"	TEXT NOT NULL UNIQUE,
	"City"	TEXT NOT NULL,
	"PostalCode"	TEXT NOT NULL,
	"Country"	TEXT NOT NULL,
	"Phone"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("SupplierID")
);
CREATE TABLE IF NOT EXISTS "Products" (
	"ProductID"	INTEGER NOT NULL UNIQUE,
	"ProductName"	TEXT NOT NULL UNIQUE,
	"SupplierID"	INTEGER NOT NULL,
	"CategoryID"	INTEGER NOT NULL,
	"Unit"	TEXT NOT NULL,
	"Price"	REAL NOT NULL,
	PRIMARY KEY("ProductID")
);
CREATE TABLE IF NOT EXISTS "Employees" (
	"EmployeeID"	INTEGER NOT NULL UNIQUE,
	"LastName"	TEXT NOT NULL,
	"FirstName"	TEXT NOT NULL,
	"BirthDate"	TEXT NOT NULL,
	"Photo"	BLOB NOT NULL UNIQUE,
	"Notes"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("EmployeeID")
);
INSERT INTO "Customers" ("CustomerID","CustomerName","ContactName","Address","City","PostalCode","Country") VALUES (1,'Alfreds Futterkiste','Maria Anders','Obere Str. 57','Berlin','12209','Germany'),
 (2,'Ana Trujillo Emparedados y helados','Ana Trujillo','Avda. de la Constitucion 2222','Mexico D.F.','05021','Mexico'),
 (3,'Antonio Moreno Taqueria','Antonio Moreno','Mataderos 2312','Mexico D.F.','05023','Mexico'),
 (4,'Around the Horn ','Thomas Hardy','120 Hanover Sq.','London','WA1 1DP','UK'),
 (5,'Berglunds snabbkop','Christina Berglund','Berguvsvagen 8','Lulea','S-958 22','Sweden');
INSERT INTO "Categories" ("CategoryID","CategoryName","Description") VALUES (1,'Beverages','Soft drinks, coffees, teas, beers, and ales'),
 (2,'Condiments','Sweet and savory sauces, relishes, spreads, and seasonings'),
 (3,'Confections','Desserts, candies, and sweet breads'),
 (4,'Dairy Products','Cheeses'),
 (5,'Grains/Cereals','Breads, crackers, pasta, and cereal');
INSERT INTO "Order Details" ("OrderDetailID","OrderID","ProductID","Quantity") VALUES (1,10248,11,12),
 (2,10248,42,10),
 (3,10248,72,5),
 (4,10249,14,9),
 (5,10249,51,40);
INSERT INTO "Orders" ("OrderID","CustomerID","EmployeeID","OrderDate","ShipperID") VALUES (10248,90,5,'7/4/1996',3),
 (10249,81,6,'7/5/1996',1),
 (10250,34,4,'7/8/1996',2),
 (10251,84,3,'7/8/1996',1),
 (10252,76,4,'7/9/1996',2);
INSERT INTO "Shippers" ("ShipperID","ShipperName","Phone") VALUES (1,'Speedy Express','(503) 555-9831'),
 (2,'United Package','(503) 555-3199'),
 (3,'Federal Shipping','(503) 555-9931');
INSERT INTO "Suppliers" ("SupplierID","SupplierName","ContactName","Address","City","PostalCode","Country","Phone") VALUES (1,'Exotic Liquid','Charlotte Cooper','49 Gilbert St.','Londona','EC1 4SD','UK','(171) 555-2222'),
 (2,'New Orleans Cajun Delights','Shelley Burke','P.O. Box 78934','New Orleans','70117','USA','(100) 555-4822'),
 (3,'Grandma Kelly''s Homestead','Regina Murphy','707 Oxford Rd.','Ann Arbor','48104','USA','(313) 555-5735'),
 (4,'Tokyo Traders','Yoshi Nagase','9-8 Sekimai Musashino-shi','Tokyo','100','Japan','(03) 3555-5011'),
 (5,'Cooperativa de Quesos ''Las Cabras;','Antonio del Valle Saavedra','Calle del Rosal 4','Oviedo','33007','Spain','(98) 598 7654');
INSERT INTO "Products" ("ProductID","ProductName","SupplierID","CategoryID","Unit","Price") VALUES (1,'Chais',1,1,'10 boxes x 20 bags',18.0),
 (2,'Chang',1,1,'24 - 12 oz bottles',19.0),
 (3,'Aniseed Syrup',1,2,'12 - 550 ml bottles',10.0),
 (4,'Chef Anton''s Cajun Seasoning',2,2,'48 - 6 oz jars',22.0),
 (5,'Chef Anton''s Gumbo Mix',2,2,'36 boxes',21.35);
INSERT INTO "Employees" ("EmployeeID","LastName","FirstName","BirthDate","Photo","Notes") VALUES (1,'Davolio','Nancy','12/8/1968','EmpID1.pic','Education includes a BA in psychology from Colorado State University. She also completed (The Art of the Cold Call). Nancy is a member of ''Toastmasters International''.'),
 (2,'Fuller','Andrew','2/19/1952','EmpID2.pic','Andrew received his BTS commercial and a Ph.D. in international marketing from the University of Dallas. He is fluent in French and Italian and reads German. He joined the company as a sales representative, was promoted to sales manager and was then named vice president of sales. Andrew is a member of the Sales Management Roundtable, the Seattle Chamber of Commerce, and the Pacific Rim Importers Association.'),
 (3,'Leverling','Janet','8/30/1963','EmpID3.pic','Janet has a BS degree in chemistry from Boston College). She has also completed a certificate program in food retailing management. Janet was hired as a sales associate and was promoted to sales representative.'),
 (4,'Peacock','Margaret','9/19/1958','EmpID4.pic','Margaret holds a BA in English literature from Concordia College and an MA from the American Institute of Culinary Arts. She was temporarily assigned to the London office before returning to her permanent post in Seattle.'),
 (5,'Bechanan','Steven','3/4/1955','EmpID5.pic','Steven Buchanan graduated from St. Andrews University, Scotland, with a BSC degree. Upon joining the company as a sales representative, he spent 6 months in an orientation program at the Seattle office and then returned to his permanent post in London, where he was promoted to sales manager. Mr. Buchanan has completed the courses ''Successful Telemarketing'' and ''International Sales Management''. He is fluent in French.');
COMMIT;
