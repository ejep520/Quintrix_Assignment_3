package com.quintrix.jepsen.erik;

class Employee {
  private int employeeId, managerId, department;
  private String firstName, lastName;
  private Address mailingAddress, homeAddress;

  public Employee(String lastName, String firstName) {
    employeeId = -1;
    managerId = -1;
    department = -1;
    mailingAddress = null;
    homeAddress = null;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public boolean isReady() {
    return (employeeId > 0) && (department > 0) && !firstName.isEmpty() && !lastName.isEmpty()
        && ((mailingAddress != null) || (homeAddress != null));
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public int getManagerId() {
    return managerId;
  }

  public int getDepartment() {
    return department;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getMailingAddress() {
    if (mailingAddress == null)
      return "No mailing address on file.";
    return mailingAddress.getAddress();
  }

  public String getPhysicalAddress() {
    if (homeAddress == null)
      return "No home address on file.";
    return homeAddress.getAddress();
  }

  public String getName() {
    return firstName + ' ' + lastName;
  }

}
