# Cafe Order Manager

This is a simple cafe order manager built for the command line interface (CLI) using Java. The program allows users to view menu items, place orders, and view order history. It's designed to be easy to use and quick to navigate, making it a great tool for busy cafes.

## Features

- View menu items: Users can view a list of menu items with their prices and descriptions.
- Place orders: Users can place orders by selecting menu items and specifying quantities.
- View order history: Users can view a history of past orders, including the date, time, items, and total price.

## Getting Started

### Prerequisites

- Java Runtime Environment (JRE) 17
- Java Development Kit (JDK) 17 for building from source
- Gradle 7.6 or higher

### Download

To download the pre-built binary file for your operating system, go to the [Releases](https://github.com/a1383n/cafe-order-manager/releases) page on GitHub and select the latest release. You will see a list of binary files for different operating systems.

Download the binary file for your operating system and extract the ZIP file. This will create a new directory with the same name as the ZIP file. Navigate to that directory and run the cafe-order-manager executable file to start the application.

### Build

To build the application, first ensure that you have Gradle installed on your system. You can download Gradle from the official website at <https://gradle.org/install/>.

Once you have Gradle installed, you can build the application by navigating to the project directory in a terminal and running the following command:

    gradle build

This will compile the Java source code and create a binary executable file in the `build/distributions` directory.

To run the application, navigate to the `build/distributions` directory and extract the ZIP file that corresponds to your operating system. For example, if you are on a Linux system, you would run:

    unzip cafe-order-manager-linux.zip

This will create a new directory with the same name as the ZIP file. Navigate to that directory and run the `cafe-order-manager` executable file to start the application:

    ./cafe-order-manager

### Usage

When the application starts, users will see an authentication menu with two options:

1. Register
2. Login

Users can select an option by entering the corresponding number and pressing enter.

#### Register

If a new user wants to use the cafe order manager, they can select the "Register" option and follow the prompts to create a new account with a username and password.

#### Login

If an existing user wants to use the cafe order manager, they can select the "Login" option and enter their username and password when prompted. If the login is successful, the user will be taken to a main menu with different options based on their role.

#### Customer View

If the logged-in user is a customer, they will see a customer menu with the following options:

1. View menu items: Users can view a list of menu items with their prices and descriptions.
2. Place order: Users can place orders by selecting menu items and specifying quantities.
3. View order history: Users can view a history of past orders, including the date, time, items, and total price.

#### Employee View

If the logged-in user is an employee, they will see an employee menu with the following options:

1. View order list: Employees can view a list of current orders with the date, time, items, and total price.
2. View storage status: Employees can view the current stock levels of each menu item.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
