# Ticket Management System

A real-time ticket management system that simulates ticket releases by vendors and purchases by customers. The system provides live monitoring of transactions and system status through an interactive dashboard.

## Introduction
This system demonstrates concurrent ticket handling with:
- Multiple vendors releasing tickets at configurable rates
- Multiple customers purchasing tickets with customizable capacities
- Real-time transaction tracking
- Live system monitoring dashboard

## Prerequisites
- Java 21
- Maven 3.8+
- Angular CLI 16+
- SpringBoot 3.1.4

## Setup Instructions

### Backend Setup
1. Clone the repository
```bash
git clone https://github.com/yourusername/ticketing-system.git
```

2. Navigate to project directory and build
```
cd ticketing-system
mvn clean install
```

3. Run the Spring Boot application
```
mvn spring-boot:run
```

## Frontend Setup

1. Run the Spring Boot application
```
cd frontend
```

2. Install dependencies
```
npm install
```

3. Start Angular development server
```
ng serve
```

The application will be available at http://localhost:4200

## Usage Instructions

### System Configuration :

1. On first launch, enter the following configuration:
- Maximum ticket capacity
- Number of vendors and their release rates
- Number of customers and their purchase limits

### Dashboard Controls:
- Dashboard Tab: Shows real-time system status including available tickets
- Vendors Tab: Manage and monitor vendor activities
- Customers Tab: Control customer behaviors and track purchases
- Transactions Tab: View detailed ticket lifecycle from release to purchase

### Managing Vendors
1. Add new vendors with:
- Vendor ID
- Release rate (tickets per second)
2. Monitor vendor performance in real-time
3. Stop/start individual vendors as needed

### Managing Customers
1. Configure customers with:
- Customer ID
- Maximum holding capacity
- Retrieval rate (purchases per second)
2. Track customer purchase statistics
3. Control customer activities in real-time

#### The system automatically handles concurrent operations and maintains transaction consistency while providing live updates through the UI.
