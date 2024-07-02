# CRUD_in_localDB_REST_API

If we use local database for CRUD operation in Android app, we need to create a local server. In my my case i'm using Apache MySQL server using Xampp software.  

#### 1. Place the 'api_data' folder in the 'htdocs' folder. (C:\xampp\htdocs\api_data)
#### 2. Create a database 'api_data'
#### 3. Create table 'data'
#### 4. open the 'LocTry2' project in android studio and run the project.

#### Steps Creating Database and Table:
1. Create a database named 'api_data'.
2. Create 'data' table
        use sql command:
        CREATE TABLE IF NOT EXISTS `data` 
        (`id` int(50) NOT NULL AUTO_INCREMENT,
         `name` varchar(256) NOT NULL,
         `age` int(50) NOT NULL,
         `email` varchar(50) NOT NULL,
         PRIMARY KEY (`id`)
        );

3. Insert data
        use sql command:
        INSERT INTO `data` (`name`, `age`, `email`) VALUES
        ('John Doe', 21, 'johndoe@gmail.com'),
        ('David Costa', 22, 'sam.mraz1996@yahoo.com'),
        ('Todd Martell', 23, 'liliane_hirt@gmail.com'),
        ('Adela Marion', 24, 'michael2004@yahoo.com'),
        ('Matthew Popp', 29, 'krystel_wol7@gmail.com'),
        ('Alan Wallin', 28, 'neva_gutman10@hotmail.com'),
        ('Joyce Hinze', 27, 'davonte.maye@yahoo.com'),
        ('Donna Andrews', 26, 'joesph.quitz@yahoo.com'),
        ('Andrew Best', 25 , 'jeramie_roh@hotmail.com'),
        ('Joel Ogle', 24, 'summer_shanah@hotmail.com');

You can develop REST API CRUD using PHP and MySQL easily and test the API using the postman app.

Test Read API:
Method: GET
http://localhost/api_data/api/read.php

Test Create API:
Method: POST
http://localhost/api_data/api/create.php

Test Update API:
Method: PUT
http://localhost/api_data/api/update.php

Test Delete API:
Method: DELETE
http://localhost/api_data/api/delete.php

App Preview:  
![HomePage](https://github.com/mahadyhasan999/CRUD_in_localDB_REST_API/assets/61708989/d56c096c-688f-4746-8756-b11434d715d4)  

![CreatePage](https://github.com/mahadyhasan999/CRUD_in_localDB_REST_API/assets/61708989/e21256fe-75f6-4c23-b921-88120a5d8dbe)  

![EditPage](https://github.com/mahadyhasan999/CRUD_in_localDB_REST_API/assets/61708989/2bca42dd-eb6a-4959-a081-e767944b19be)  

![Database](https://github.com/mahadyhasan999/CRUD_in_localDB_REST_API/assets/61708989/fb0dc41c-5014-4a1d-af0b-6e221faa89cf)  

