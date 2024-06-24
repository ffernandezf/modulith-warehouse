@org.springframework.modulith.ApplicationModule(allowedDependencies = { "product", "product::model", "customer", "customer::model" })
// Teh above notation is optional and defines all the imported interfaces
package com.personal.warehouse.order;