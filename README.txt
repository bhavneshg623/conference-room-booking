# Conference-room-booking

Description
---------------

Conference-room-booking is service to enable user to book conference room and check availability.
It provides endpoints for find available conference room and Book conference room.

| Operation | Description | endpoint |
| ----------- | ----- | ----------- | 
| GET | Fetch available conference room based on time range | /internal/v1/conferencerooms?startTime={}&endTime={}|
| POST | Book conference room based on capacity and time range provided | /internal/v1/conferenceroom/book |

databse details
---------------
Please check Schema.sql and data.sql for database tables and data in tables. 

