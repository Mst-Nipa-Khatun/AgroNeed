CREATE DATABASE Final_E_Commerce;
USE Final_E_Commerce;

create table role
(
    id        int auto_increment
        primary key,
    name      varchar(128) null,
    status    int          null,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    createdBy varchar(255) null,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updatedBy varchar(255) null,
    constraint UK4bssh5141cg7snenoxyxfch4a
        unique (name)
);

-- auto-generated definition
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       phone varchar(20) not null ,
                       email VARCHAR(255) default null,
                       status integer,
                       password_hash VARCHAR(255) NOT NULL,
                       address varchar(255),
                       createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       createdBy varchar(255) null,
                       updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       updatedBy varchar(255) null
);


create table users_role
(
    users_id int not null,
    roles_id int not null,
    constraint role_id_mapping_constraint
        foreign key (roles_id) references role (id),
    constraint users_id_mapping_constraint
        foreign key (users_id) references users (id)
);

CREATE TABLE Products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          stock INT NOT NULL,
                          status integer,
                          category VARCHAR(255),
                          image_url VARCHAR(255),
                          createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          createdBy varchar(255) null,
                          updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          updatedBy varchar(255) null
);

CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            parent_id INT NULL,
                            status integer,
                            createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            createdBy varchar(255) null,
                            updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            updatedBy varchar(255) null,
                            FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE
);


CREATE TABLE product_categories (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    product_id INT NOT NULL,
                                    category_id INT NOT NULL,
                                    status integer,
                                    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    createdBy varchar(255) null,
                                    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    updatedBy varchar(255) null,
                                    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);



CREATE TABLE Orders (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT,
                        total_price DECIMAL(10,2),
                        numbers_of_products INT,
                        address VARCHAR(255) NOT NULL,
                        phone_number VARCHAR(255) NOT NULL,
                        payment_method INT NOT NULL ,
--     status ENUM('pending', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
                        status integer,
                        createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        createdBy varchar(255) null,
                        updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        updatedBy varchar(255) null,
                        FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Order_Items (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             order_id INT,
                             product_id INT,
                             quantity INT,
                             status integer,
                             price DECIMAL(10,2),
                             createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             createdBy varchar(255) null,
                             updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             updatedBy varchar(255) null,
                             FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);

CREATE TABLE Shopping_Cart (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT,
                               product_id INT,
                               status integer,
                               quantity INT,
                               createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               createdBy varchar(255) null,
                               updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               updatedBy varchar(255) null,
                               FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);


CREATE TABLE Payments (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          order_id INT,
                          user_id INT,
                          payment_method ENUM('PayPal', 'Stripe', 'Credit Card','Cash On'),
                          amount DECIMAL(10,2),
--     status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
                          status integer,
                          createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          createdBy varchar(255) null,
                          updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          updatedBy varchar(255) null,
                          FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Reviews (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT,
                         product_id INT,
                         status integer,
                         rating INT CHECK (rating BETWEEN 1 AND 5),
                         comment TEXT,
                         createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         createdBy varchar(255) null,
                         updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         updatedBy varchar(255) null,
                         FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                         FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);

CREATE TABLE Suppliers
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    email     VARCHAR(255) ,
    phone     VARCHAR(50),
    address   VARCHAR(255),
    status    integer,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    createdBy VARCHAR(255) NULL,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updatedBy VARCHAR(255) NULL
);

CREATE TABLE Supplier_Products
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT NOT NULL,
    product_id  INT NOT NULL,
    price       DECIMAL(10,2) NULL,
    status      integer,
    createdAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    createdBy   VARCHAR(255) NULL,
    updatedAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updatedBy   VARCHAR(255) NULL,
    FOREIGN KEY (supplier_id) REFERENCES Suppliers (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE
);



CREATE TABLE Product_Comments (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  user_id INT NOT NULL,
                                  product_id INT NOT NULL,
                                  comment TEXT NOT NULL,
                                  status INT DEFAULT 1, -- e.g., 1=active, 0=deleted/hidden
                                  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  createdBy VARCHAR(255) NULL,
                                  updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  updatedBy VARCHAR(255) NULL,
                                  FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                                  FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);


