CREATE TABLE `User` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE Discount (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `desc` TEXT,  -- Use backticks or change the column name since `desc` is a reserved keyword in SQL
    discountPercent DOUBLE NOT NULL,
    active BOOLEAN NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP NULL
);

CREATE TABLE ProductCategory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `desc` TEXT,  -- Use backticks or change the column name since `desc` is a reserved keyword in SQL
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP NULL
);

CREATE TABLE ProductInventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP NULL
);

CREATE TABLE Product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `desc` TEXT,  -- Use backticks or change the column name since `desc` is a reserved keyword in SQL
    categoryId BIGINT,
    inventoryId BIGINT,
    discountId BIGINT,
    price DOUBLE NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP NULL,

    CONSTRAINT fk_product_category FOREIGN KEY (categoryId) REFERENCES ProductCategory(id),
    CONSTRAINT fk_product_inventory FOREIGN KEY (inventoryId) REFERENCES ProductInventory(id),
    CONSTRAINT fk_product_discount FOREIGN KEY (discountId) REFERENCES Discount(id)
);

CREATE TABLE UserAddress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postalCode VARCHAR(20) NOT NULL,
    county VARCHAR(100) NOT NULL,
    mobile VARCHAR(20) NOT NULL,

    CONSTRAINT fk_useraddress_user FOREIGN KEY (userId) REFERENCES `User`(id)
);


CREATE TABLE UserPayment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    paymentType VARCHAR(50) NOT NULL,
    provider VARCHAR(100) NOT NULL,
    accountNo VARCHAR(100) NOT NULL,
    expiry DATE NOT NULL,

    CONSTRAINT fk_userpayment_user FOREIGN KEY (userId) REFERENCES `User`(id)
);


CREATE TABLE CartItem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    productId BIGINT,
    quantity INT NOT NULL,
    createdAt TIMESTAMP,
    modifiedAt TIMESTAMP,
    CONSTRAINT FK_Product
        FOREIGN KEY (productId)
        REFERENCES Product(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE PaymentDetails (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderId BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    provider VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE OrderDetails (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    total DOUBLE NOT NULL,
    paymentId BIGINT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orderdetails_user FOREIGN KEY (userId) REFERENCES `User`(id),
    CONSTRAINT fk_orderdetails_payment FOREIGN KEY (paymentId) REFERENCES PaymentDetails(id)
);
CREATE TABLE OrderItem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    quantity INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orderitem_order FOREIGN KEY (orderId) REFERENCES OrderDetails(id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (productId) REFERENCES Product(id)
);


