package com.quintrix.jepsen.erik;

class Address {
  private String address1, address2, city, zip;
  private State state;

  public enum State {
    ALABAMA, ALASKA, ARIZONA, ARKANSAS, CALIFORNIA, COLORADO, CONNECTICUT, DELEWARE, FLORIDA, GEORGIA, HAWAII, IDAHO, ILLINOIS, INDIANA, IOWA, KANSAS, KENTUCKY, LOUISIANA, MAINE, MARYLAND, MASSACHUSETTS, MICHIGAN, MINNESOTA, MISSISSIPPI, MISSOURI, MONTANA, NEBRASKA, NEVADA, NEW_HAMSHIRE, NEW_JERSEY, NEW_MEXICO, NEW_YORK, NORTH_CAROLINA, NORTH_DAKOTA, OHIO, OKLAHOMA, OREGON, PENNSYLVANIA, RHODE_ISLAND, SOUTH_CAROLINA, SOUTH_DAKOTA, TENNESSEE, TEXAS, UTAH, VERMONT, VIRGINIA, WASHINGTON, WEST_VIRGINIA, WISCONSIN, WYOMING, DISTRICT_OF_COLUMBIA, AMERICAN_SAMOA, GUAM, NORTHERN_MARIANA_ISLANDS, PUERTO_RICO, US_VIRGIN_ISLANDS
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getAddress() {
    return address1 + System.lineSeparator() + address2 + System.lineSeparator() + city + ' '
        + state.name() + zip;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state.name();
  }

  public String getZip() {
    return zip;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setState(State state) {
    this.state = state;
  }
}
