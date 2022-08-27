package com.quintrix.jepsen.erik;

class Department {
  private String name;
  private Address address;
  private int deptNumber;

  public Department(String name) {
    this.name = name;
    address = null;
    deptNumber = -1;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    if (address == null)
      return "No address on file.";
    return address.getAddress();
  }

  public int getDeptNumber() {
    return deptNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setDeptNumber(int deptNumber) {
    this.deptNumber = deptNumber;
  }
}
