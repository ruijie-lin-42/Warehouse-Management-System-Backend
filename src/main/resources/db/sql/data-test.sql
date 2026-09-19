-- category
insert into category(name, parent_id)
values ('Electronics', null),
('Food', null),
('Clothing', null),
('Home', null),
('Office', null),
('Health', null),
('Sports', null);
insert into category(name, parent_id)
values ('Computers', 1),('Mobiles', 1),('Accessories', 1),('Audio', 1),
('Vegetable', 2),('Meat', 2),('Drink', 2),('Snack', 2),
('Men', 3),('Women', 3),
('Furniture', 4),('Kitchen', 4), ('Decorations', 4),
('Desk Essentials', 5),('Paper & Filing', 5),('Technology', 5),
('Medicine', 6),('Nutrition', 6),('First Aid', 6),
('Apparel', 7),('Footwear', 7),('Protection', 7),('Equipment', 7);

-- items info
INSERT INTO item_info
(name, description, category_id, unit, sku, price)
VALUES
-- Electronics - Computers (8)
('Lenovo ThinkPad X1 Carbon Laptop', 'Business ultrabook laptop for office work', 8, 'piece', 'COMP-LENOVO-X1', 1800),
('Dell XPS 13 Laptop', 'Premium compact laptop for productivity', 8, 'piece', 'COMP-DELL-XPS13', 1600),
('MacBook Air M2', 'Lightweight Apple laptop with M2 chip', 8, 'piece', 'COMP-MACBOOK-M2', 1500),
('ASUS ROG Gaming Laptop', 'Gaming laptop with dedicated graphics card', 8, 'piece', 'COMP-ASUS-ROG', 2200),

-- Electronics - Mobiles (9)
('iPhone 15 Pro', 'Flagship Apple smartphone', 9, 'piece', 'MOB-IPHONE15PRO', 1200),
('Samsung Galaxy S24', 'Premium Android smartphone', 9, 'piece', 'MOB-SAMSUNG-S24', 1000),
('Google Pixel 8', 'Google Android smartphone with AI features', 9, 'piece', 'MOB-PIXEL8', 850),
('OnePlus 12', 'High performance Android smartphone', 9, 'piece', 'MOB-ONEPLUS12', 800),

-- Electronics - Accessories (10)
('Logitech MX Master 3 Mouse', 'Wireless productivity mouse', 10, 'piece', 'ACC-MXMASTER3', 120),
('Anker USB-C Hub', 'USB-C hub for laptop expansion', 10, 'piece', 'ACC-ANKER-HUB', 60),
('Apple Magic Keyboard', 'Wireless keyboard for Apple devices', 10, 'piece', 'ACC-APPLE-KEYBOARD', 100),
('Samsung 1TB SSD External Drive', 'Portable solid state storage drive', 10, 'piece', 'ACC-SAMSUNG-SSD1TB', 150),

-- Electronics - Audio (11)
('Sony WH-1000XM5 Headphones', 'Noise cancelling wireless headphones', 11, 'piece', 'AUD-SONY-XM5', 350),
('AirPods Pro 2', 'Wireless earbuds with active noise cancellation', 11, 'piece', 'AUD-AIRPODS2', 250),
('JBL Flip 6 Speaker', 'Portable Bluetooth speaker', 11, 'piece', 'AUD-JBL-FLIP6', 130),
('Bose QuietComfort Earbuds', 'Premium wireless earbuds', 11, 'piece', 'AUD-BOSE-QC', 280),

-- Food - Vegetable (12)
('Carrot 1kg Pack', 'Fresh carrots in 1kg package', 12, 'pack', 'VEG-CARROT-1KG', 5),
('Fresh Broccoli', 'Fresh green broccoli', 12, 'piece', 'VEG-BROCCOLI', 4),
('Tomato Box 2kg', 'Fresh tomatoes packed in box', 12, 'box', 'VEG-TOMATO-2KG', 8),
('Potato Sack 5kg', 'Potatoes packed in large sack', 12, 'pack', 'VEG-POTATO-5KG', 12),

-- Food - Meat (13)
('Chicken Breast 1kg', 'Fresh boneless chicken breast', 13, 'kg', 'MEAT-CHICKEN-1KG', 14),
('Beef Ribeye Steak 500g', 'Premium ribeye steak cut', 13, 'piece', 'MEAT-RIBEYE-500G', 20),
('Pork Belly Slice 1kg', 'Fresh sliced pork belly', 13, 'kg', 'MEAT-PORKBELLY-1KG', 16),
('Salmon Fillet 300g', 'Fresh salmon fillet portion', 13, 'piece', 'MEAT-SALMON-300G', 18),

-- Food - Drink (14)
('Coca Cola 330ml', 'Carbonated soft drink can', 14, 'bottle', 'DRINK-COKE-330', 3),
('Orange Juice 1L', 'Fresh orange juice bottle', 14, 'bottle', 'DRINK-OJ-1L', 6),
('Green Tea Bottle', 'Ready to drink green tea', 14, 'bottle', 'DRINK-GREENTEA', 4),
('Mineral Water 500ml', 'Bottled mineral water', 14, 'bottle', 'DRINK-WATER-500', 2),

-- Food - Snack (15)
('Lay’s Original Chips', 'Classic potato chips snack', 15, 'pack', 'SNACK-LAYS-ORIGINAL', 4),
('Oreo Cookies Pack', 'Chocolate sandwich cookies', 15, 'pack', 'SNACK-OREO', 5),
('Mixed Nuts 500g', 'Assorted mixed nuts pack', 15, 'pack', 'SNACK-MIXEDNUTS', 12),
('Chocolate Bar (Dairy Milk)', 'Milk chocolate snack bar', 15, 'piece', 'SNACK-DAIRYMILK', 3),

-- Clothing - Men (16)
('Nike Men’s Running T-Shirt', 'Breathable sports t-shirt for men', 16, 'piece', 'MEN-NIKE-TSHIRT', 35),
('Levi’s 501 Jeans', 'Classic men jeans', 16, 'piece', 'MEN-LEVIS-501', 70),
('Uniqlo Hoodie Men', 'Comfortable casual hoodie', 16, 'piece', 'MEN-UNIQLO-HOODIE', 45),
('Adidas Track Pants', 'Athletic pants for training', 16, 'piece', 'MEN-ADIDAS-PANTS', 50),

-- Clothing - Women (17)
('Zara Women Dress', 'Casual women dress', 17, 'piece', 'WOMEN-ZARA-DRESS', 60),
('H&M Blouse', 'Fashionable women blouse', 17, 'piece', 'WOMEN-HM-BLOUSE', 35),
('Lululemon Leggings', 'Stretchable sports leggings', 17, 'piece', 'WOMEN-LULULEMON-LEGGINGS', 90),
('Mango Summer Skirt', 'Lightweight summer skirt', 17, 'piece', 'WOMEN-MANGO-SKIRT', 40),

-- Home - Furniture (18)
('IKEA Office Chair', 'Ergonomic office chair for home use', 18, 'piece', 'FURN-IKEA-CHAIR', 120),
('Wooden Dining Table', 'Solid wood dining table', 18, 'piece', 'FURN-DINING-TABLE', 300),
('Sofa 3-Seater Grey', 'Three seat living room sofa', 18, 'piece', 'FURN-SOFA-3S', 450),
('Bed Frame Queen Size', 'Queen size bed frame', 18, 'piece', 'FURN-BED-QUEEN', 400),

-- Home - Kitchen (19)
('Non-stick Frying Pan', 'Kitchen frying pan with non-stick coating', 19, 'piece', 'KITCH-PAN-NONSTICK', 25),
('Stainless Steel Knife Set', 'Multi-piece kitchen knife set', 19, 'pack', 'KITCH-KNIFE-SET', 40),
('Electric Kettle', 'Fast boiling electric kettle', 19, 'piece', 'KITCH-KETTLE', 30),
('Rice Cooker 5L', 'Large capacity rice cooker', 19, 'piece', 'KITCH-RICECOOKER', 60),

-- Home - Decorations (20)
('Wall Art Painting Set', 'Decorative wall painting set', 20, 'pack', 'DECOR-WALL-ART', 50),
('LED Ambient Light Strip', 'RGB LED light strip for room lighting', 20, 'piece', 'DECOR-LED-STRIP', 20),
('Ceramic Flower Vase', 'Decorative ceramic vase', 20, 'piece', 'DECOR-VASE', 15),
('Table Lamp Warm Light', 'Warm light bedside table lamp', 20, 'piece', 'DECOR-LAMP', 25),

-- Office - Desk Essentials (21)
('Stapler Heavy Duty', 'Strong metal stapler for office use', 21, 'piece', 'OFF-STAPLER', 10),
('Pen Set Black Ink', 'Set of black ink pens', 21, 'pack', 'OFF-PEN-SET', 8),
('Notebook A5 Hardcover', 'Hardcover A5 notebook', 21, 'piece', 'OFF-NOTEBOOK-A5', 6),
('Desk Organizer Tray', 'Desk storage organizer tray', 21, 'piece', 'OFF-ORGANIZER', 12),

-- Office - Paper & Filing (22)
('A4 Printing Paper Ream', 'Standard A4 paper pack', 22, 'pack', 'OFF-PAPER-A4', 7),
('File Folder Pack', 'Document file folders set', 22, 'pack', 'OFF-FOLDER', 9),
('Binder Clips Set', 'Assorted binder clips', 22, 'pack', 'OFF-CLIPS', 5),
('Sticky Notes Color Pack', 'Color sticky notes set', 22, 'pack', 'OFF-STICKYNOTES', 4),

-- Office - Technology (23)
('HP Laser Printer', 'Monochrome laser printer', 23, 'piece', 'OFF-HP-PRINTER', 150),
('Logitech Webcam C920', 'HD USB webcam', 23, 'piece', 'OFF-WEBCAM', 70),
('USB Flash Drive 64GB', 'Portable USB storage device', 23, 'piece', 'OFF-USB64GB', 15),
('Wireless Router TP-Link', 'Home WiFi router', 23, 'piece', 'OFF-ROUTER', 40),

-- Health - Medicine (24)
('Ibuprofen 200mg', 'Pain relief tablets', 24, 'tablet', 'MED-IBUPROFEN', 6),
('Vitamin C Tablets', 'Immune support supplement', 24, 'tablet', 'MED-VITC', 8),
('Antihistamine Tablets', 'Allergy relief medication', 24, 'tablet', 'MED-ANTIHIST', 7),
('Cough Syrup', 'Liquid cough medicine', 24, 'bottle', 'MED-COUGH', 9),

-- Health - Nutrition (25)
('Protein Powder Whey', 'Whey protein supplement', 25, 'pack', 'NUT-PROTEIN', 40),
('Multivitamin Capsules', 'Daily multivitamin supplement', 25, 'tablet', 'NUT-MULTIVIT', 12),
('Omega-3 Fish Oil', 'Fish oil capsules supplement', 25, 'tablet', 'NUT-OMEGA3', 15),
('Creatine Monohydrate', 'Fitness supplement powder', 25, 'pack', 'NUT-CREATINE', 25),

-- Health - First Aid (26)
('Bandage Roll', 'Medical bandage roll', 26, 'piece', 'FA-BANDAGE', 3),
('Disinfectant Spray', 'Antiseptic spray bottle', 26, 'bottle', 'FA-DISINFECT', 5),
('Medical Gloves', 'Disposable medical gloves', 26, 'pack', 'FA-GLOVES', 6),
('First Aid Kit Box', 'Emergency medical kit', 26, 'box', 'FA-KIT', 20),

-- Sports - Apparel (27)
('Nike Sports Shorts', 'Breathable sports shorts', 27, 'piece', 'SP-NIKE-SHORTS', 25),
('Adidas Compression Shirt', 'Compression training shirt', 27, 'piece', 'SP-ADIDAS-COMP', 30),
('Under Armour Training Pants', 'Sports training pants', 27, 'piece', 'SP-UA-PANTS', 35),
('Sports Socks Pack', 'Athletic socks set', 27, 'pack', 'SP-SOCKS', 10),

-- Sports - Footwear (28)
('Nike Air Zoom Running Shoes', 'Running sports shoes', 28, 'pair', 'SHOE-NIKE-ZOOM', 90),
('Adidas Ultraboost', 'Premium running shoes', 28, 'pair', 'SHOE-ADIDAS-UB', 120),
('Puma Training Sneakers', 'Training sneakers', 28, 'pair', 'SHOE-PUMA-TRAIN', 80),
('Converse All Star', 'Classic canvas sneakers', 28, 'pair', 'SHOE-CONVERSE', 70),

-- Sports - Protection (29)
('Knee Pads Set', 'Sports knee protection pads', 29, 'pair', 'PRO-KNEE', 15),
('Elbow Guards', 'Elbow protection gear', 29, 'pair', 'PRO-ELBOW', 12),
('Sports Helmet Cycling', 'Protective cycling helmet', 29, 'piece', 'PRO-HELMET', 40),
('Wrist Support Band', 'Wrist support brace', 29, 'piece', 'PRO-WRIST', 8),

-- Sports - Equipment (30)
('Basketball Official Size 7', 'Standard basketball', 30, 'piece', 'EQ-BASKETBALL', 25),
('Football (Soccer Ball)', 'Standard soccer ball', 30, 'piece', 'EQ-SOCCER', 20),
('Yoga Mat Non-slip', 'Fitness yoga mat', 30, 'piece', 'EQ-YOGAMAT', 18),
('Dumbbell 10kg Pair', 'Pair of 10kg dumbbells', 30, 'pair', 'EQ-DUMBBELL-10', 50);

