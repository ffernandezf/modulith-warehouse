# modulith-warehouse
An example on the use of Spring-modulith by creating a small inventory application named warehouse

This repository sets a small inventory applications with 3 domains: Customer, Product and Order.

The Order is created for a given customer and contains as many products as needed en different item lines.

The repository contains three branches:
- master branch --> the initial project skeleton. With 2 modules (products and Customer) that has no relationship between them
- modulith-initial branch --> inital setup with modulith. Order module is added and uses product and customer but modulith tests fails due to non-exposed types
- modulith-events-solution --> working solution with modulith. Order module uses imports allowed by modulith and modules separatin is complete. Communication among modules is done via events.

