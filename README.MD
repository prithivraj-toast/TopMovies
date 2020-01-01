Notes : 

1. Navigation is done purely using deep links in this implementation. This can provide a clean separation of features when implementing something like a dynamic app delivery where modules are loaded at runtime.

2. There is a 'data' module that is feature agnostic and stores and retrieves data from the server/local. This is only for convenience so as to not repeat the database and network setup in every feature module. 

3. The database is the single source of truth in this implementation - the principle being every pixel of UI should be traceable back to a row in the database.

4. Repositories are left to feature modules because it can vary within the same app based on the use case. A user info screen can be offline first where as a currency exchange rate screen need not be.