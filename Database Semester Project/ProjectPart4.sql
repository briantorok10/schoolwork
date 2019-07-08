--Query 1
--Looks for items in the item table that are more expensive than $500 and have
--a profit margin of at least $100
SELECT item_name AS "Item", profit_margin AS "Profit Margin"
FROM items
WHERE price > 500 AND profit_margin > 100
ORDER BY profit_margin DESC;

--Query 2
--Displays if item stocks in each store are out of stock,
--need to be replenished, or have sufficient stock
SELECT item_id AS "Item No.", inventory AS "Stock",
    (CASE WHEN inventory = 0 THEN 'Out of Stock'
          WHEN inventory < 10 THEN 'Low on Stock'
          ELSE 'Stock Sufficient'
    END) AS "Stock Status"
FROM item_location
ORDER BY inventory;

--Query 3
--Finds the total number of items in stock at a specific store
SELECT SUM(inventory)AS "Total Inventory"
FROM item_location
WHERE loc_id = 1002111
ORDER BY 1;

--Query 4
--Joins the sales_associate table and store_location table to find what employee works where
SELECT sa.last_name ||  ', ' ||  sa.first_name AS "Employee Name", loc_id AS "Store No."
FROM sales_associate sa
JOIN store_location sl USING (loc_id)
ORDER BY sa.last_name;

--Query 5
--Finds item inventories whose prices are more than average 
SELECT item_name AS "Item", price AS "Price"
FROM items
WHERE price > (SELECT AVG(price)
               FROM items);

--Query 6
--Grabs the zip code and id for all sales associates and customers
SELECT employee_id, zip
FROM sales_associate
UNION
SELECT cust_id, zip
FROM customer;

--Query 7
--

--Query 8
--Grabs the zip code and id for all sales associates and stores
SELECT employee_id AS "ID Number", to_char(zip) AS "Zip Code"
FROM sales_associate
UNION
SELECT loc_id, zip
FROM store_location;

--Query 9
--Finds stores that have items above a certain price threshold
SELECT loc_id AS "Store Number"
FROM store_location
WHERE loc_id IN (SELECT loc_id
                 FROM item_location
                 WHERE item_id IN(SELECT item_id
                                  FROM items
                                  WHERE price > 100));

--Query 10
--Finds all employees and the store they work at
SELECT sa.employee_id AS "Employee ID", sa.last_name AS "Last Name",
    sl.loc_id AS "Store ID"
FROM sales_associate sa FULL OUTER JOIN store_location sl
ON (sa.loc_id = sl.loc_id)
ORDER BY sa.last_name;