# Insurance Management System

A Java-based WAR application for managing insurance policies, claims, customers, and payments. Designed for deployment on Apache Tomcat with MySQL as the backend database.

## Features

- **Customer Management**: Register and manage customer profiles
- **Policy Management**: Create and track insurance policies (Health, Auto, Home, Life)
- **Claims Processing**: File and track insurance claims
- **Payment Processing**: Record and monitor policy payments
- **Agent Management**: Manage insurance agents and commission rates

## Technology Stack

- **Language**: Java 11
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9+
- **Database**: MySQL 8.0+
- **Servlets/JSP**: Java EE 4.0
- **JDBC Driver**: MySQL Connector/J 8.0.33

## Project Structure

```
insurance-app/
├── src/main/
│   ├── java/com/insurance/
│   │   ├── util/         # Database utilities
│   │   ├── model/        # Data models (Customer, Policy, Claim, etc.)
│   │   ├── dao/          # Data Access Objects
│   │   └── servlet/      # HTTP Servlets
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml   # Deployment descriptor
│       └── *.jsp         # JSP pages
├── pom.xml               # Maven configuration
└── db.sql                # Database schema and sample data
```

## Prerequisites

1. **Java 11 or higher** - [Download JDK](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
2. **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
3. **Apache Tomcat 9+** - [Download Tomcat](https://tomcat.apache.org/download-90.cgi)
4. **MySQL Server 8.0+** - [Download MySQL](https://dev.mysql.com/downloads/mysql/)

## Installation & Setup

### 1. Database Setup

```bash
# Start MySQL server and run the schema script
mysql -u root -p < db.sql
```

This creates:
- Database: `insurance_db`
- Tables: customers, policies, claims, payments, agents
- Sample data for testing

### 2. Database Configuration

Update database credentials in `src/main/java/com/insurance/util/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/insurance_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";  // Change this
```

### 3. Build the Application

```bash
# Clean and build the WAR file
mvn clean package
```

This generates: `target/insurance-app.war`

### 4. Deploy on Tomcat

**Option A: Manual Deployment**
```bash
cp target/insurance-app.war /path/to/tomcat/webapps/
```

**Option B: Using Maven Plugin**
```bash
# Make sure Tomcat credentials are configured in ~/.m2/settings.xml
mvn tomcat7:deploy
```

### 5. Start Tomcat

```bash
# Linux/Mac
/path/to/tomcat/bin/startup.sh

# Windows
C:\path\to\tomcat\bin\startup.bat
```

### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8080/insurance-app/
```

## API Endpoints

### Customers
- `GET /insurance-app/customers` - List all customers
- `GET /insurance-app/customers?action=view&id=1` - View customer details
- `POST /insurance-app/customers?action=add` - Add new customer
- `POST /insurance-app/customers?action=update` - Update customer
- `POST /insurance-app/customers?action=delete` - Delete customer

### Policies
- `GET /insurance-app/policies` - List all policies
- `POST /insurance-app/policies?action=add` - Add new policy

### Claims
- `GET /insurance-app/claims` - List all claims
- `POST /insurance-app/claims?action=add` - File a new claim

### Payments
- `GET /insurance-app/payments` - List all payments
- `POST /insurance-app/payments?action=add` - Record new payment

## Sample Data

The `db.sql` file includes:
- 3 sample customers (John Doe, Jane Smith, Robert Johnson)
- 4 sample policies (Auto, Home, Health, Life)
- 2 sample agents
- 3 sample payments

## Development

### Adding a New Feature

1. Create model class in `src/main/java/com/insurance/model/`
2. Create DAO class in `src/main/java/com/insurance/dao/`
3. Create servlet in `src/main/java/com/insurance/servlet/`
4. Update `web.xml` if needed
5. Add JSP views in `src/main/webapp/`

### Building and Testing

```bash
# Build
mvn clean package

# Run tests
mvn test

# Check for code issues
mvn spotbugs:check
```

## Database Schema

### Customers Table
- customer_id (INT, PK, Auto-increment)
- first_name, last_name (VARCHAR)
- email (VARCHAR, UNIQUE)
- phone, address, city, state, zip_code
- date_of_birth (DATE)
- Timestamps

### Policies Table
- policy_id (INT, PK)
- customer_id (FK)
- policy_number (VARCHAR, UNIQUE)
- policy_type (ENUM: Health, Auto, Home, Life)
- coverage_amount, premium_amount (DECIMAL)
- start_date, end_date (DATE)
- status (ENUM: Active, Inactive, Expired, Cancelled)

### Claims Table
- claim_id (INT, PK)
- policy_id (FK)
- claim_number (VARCHAR, UNIQUE)
- claim_amount, approved_amount (DECIMAL)
- claim_date, approval_date (DATE)
- status (ENUM: Pending, Approved, Rejected, Closed)

### Payments Table
- payment_id (INT, PK)
- policy_id (FK)
- amount (DECIMAL)
- payment_date (DATE)
- payment_method (ENUM)
- status (ENUM: Pending, Completed, Failed)

## Configuration Files

### pom.xml
Maven build configuration with dependencies:
- javax.servlet-api
- jsp-api
- mysql-connector-java
- junit

### web.xml
Deployment descriptor:
- Welcome files: index.jsp
- Error pages: error.jsp for 404 and 500 errors

## Troubleshooting

### MySQL Connection Issues
- Verify MySQL server is running
- Check database credentials in DBConnection.java
- Ensure `insurance_db` database exists

### WAR Deployment Issues
- Check Tomcat logs: `catalina.out`
- Verify WAR file is in webapps directory
- Restart Tomcat after deployment

### Port Conflicts
- Default Tomcat port: 8080
- Change in `conf/server.xml` if needed

## Future Enhancements

- User authentication and authorization
- Dashboard with analytics
- Email notifications for claims
- Mobile app integration
- Advanced reporting features
- API documentation (Swagger)

## License

This project is provided as-is for educational and commercial use.

## Support

For issues or questions, please create an issue in the GitHub repository.

---

**Ready to Deploy:** This application is production-ready and can be deployed on any standard Tomcat server with MySQL backend.
