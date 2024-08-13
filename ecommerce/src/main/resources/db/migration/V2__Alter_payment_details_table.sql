ALTER TABLE PaymentDetails
ADD CONSTRAINT fk_paymentdetails_order
FOREIGN KEY (orderId) REFERENCES OrderDetails(id);