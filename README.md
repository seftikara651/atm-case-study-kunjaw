# Mini Project Practice: Console Based ATM

Please follow this instruction **before** you work on this project:

1. Clone this project
2. Create a new branch, using your username as the branch name
3. Edit this form below to match your identity, commit as "init"
4. Push to the same repository
5. Read thoroughly every aspects and details of the project
6. Now you can go on, **GOOD LUCK** in the process!

---

```plaintext
Full Name:    { PUT YOUR NAME HERE }
Organization: { PUT YOUR ORGANIZATION NAME HERE }
```

---

## Heads Up

> The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL
> NOT", "SHOULD", "SHOULD NOT", "RECOMMENDED",  "MAY", and
> "OPTIONAL" in this document are to be interpreted as described in
> [RFC 2119](https://datatracker.ietf.org/doc/html/rfc2119).

## Development Requirements

- JDK 11 _(Using OpenJDK or Zulu is RECOMMENDED)_
- Maven 3.8+

## Execution Instruction

- You SHOULD make sure that you've met development requirements before you continue
- It is RECOMMENDED that you read the whole source code to better understand the structure and flow of the template
- You MUST implement features listed under Feature Checklists and SHALL follow rules defined under Constraints, within a timespan of 4 hours
- All specifications of this project are REQUIRED to be implemented
- You MAY start developing the application on `com.tujuhsembilan.App` file, at `start` method
- You MAY modify all source files in this template, without exception, to accomodate your needs; for example: to fix occuring bugs, to match your style of coding, to streamline the program, etc.
- In case of difficulties, you MAY ask any person in charge of monitoring this test
- You MAY also browse the internet to find technical support
- You SHOULD NOT discuss about this project to your colleagues
- It is RECOMMENDED for you to commit your progress on small scale; for example: you commit every time you've done a single feature
- Pushing to repository after every commit is OPTIONAL
- **After you finished this project**, you MAY submit about what you think of this project, or even your personal experience, in the review form at the bottom; be creative! and then you can commit and push once more :)

## Feature Checklists

- [ ] ATM are provided from 4 different banks:

  - [ ] BRI
  - [ ] BNI
  - [ ] Mandiri
  - [ ] BCA

  each having its own managed customer accounts and money stocks.

- [ ] Every time a Customer accesses the ATM, they should input their account number and pin, before they are able to use its feature.

- [ ] All Customers are able to use whichever ATM they please. However, there will be additional charge applied for criss-cross usage.

- [ ] Each ATM have the same set of currency nominals in Rupiah, and the same set of features:

  - [ ] Account Balance Information

    This feature shows current account's balance

  - [ ] Money Withdrawal

    This feature allows Customer to withdraw money from their registered account in certain Banks, with a custom withdraw amount

    This feature will tells Customer to take certain number of currency nominals in descending priority based on value, relative to the withdrawn amount; for example: 1x of Nominal C, 3x of Nominal B, 1x of Nominal A

    This feature will finally shows remaining account's balance

  - [ ] Phone Credits Top-Up

    This feature allows Customer to top-up credit for inputed phone number, at these amount:

    - [ ] Rp10.000,00
    - [ ] Rp20.000,00
    - [ ] Rp50.000,00
    - [ ] Rp100.000,00

    This feature will finally shows target phone number, topped-up amount, and remaining account's balance

  - [ ] Electricity Bills Token

    This feature allows Customer to buy electricity bills token for inputed bill number, at these amount:

    - [ ] Rp50.000,00
    - [ ] Rp100.000,00
    - [ ] Rp200.000,00
    - [ ] Rp500.000,00

    This feature will then tells the Customer of the token, target bill number, and remaining account's balance

  - [ ] Account Mutation (Fund Transfer)

    This feature allows Customer to transfer funds into certain account number on selected Banks

    This feature will finally shows target Bank and account, transfered amount, and remaining account's balance

  and additionaly for BNI and Mandiri, both will have this feature:

  - [ ] Money Deposit

    This feature allows Customer to deposit money into their account

    This feature will finally shows current account's balance

## Constraints

- [ ] The ATM is assumed to be always active, and MUST NOT be able to be turned off
- [ ] The ATM MUST only accept number as the input
- [ ] In case of login failure, Customer MUST have a maximum of 3 retries, and further login attempt SHALL be blocked
- [ ] Money stock on ATM MUST be finite

  The default amount of stocked money MUST be Rp25.000.000,00
- [ ] Maximum amount per transaction MUST be Rp2.500.000,00
- [ ] Maximum daily transaction per account MUST be Rp5.000.000,00
- [ ] Any maximum expense limitation SHALL only applies on physical transaction, meaning virtual transaction like top-up and transfers MUST NOT be affected
- [ ] Each account MUST have minimum residue balance of Rp10.000,00
- [ ] The ATM MUST have these currency nominals:

  - [ ] Rp10.000,00
  - [ ] Rp20.000,00
  - [ ] Rp50.000,00
  - [ ] Rp100.000,00

  There is no individual finite amount for these nominals, and all nominals is assumed to be always available on every ATMs
- [ ] Money Withdrawal and Money Deposit feature SHALL only accept input for values that is multiples of 10.000
- [ ] Phone number MUST be between 3 and 15 digits
- [ ] Criss-cross transaction fee MUST be Rp2.500
- [ ] Every errors and/or invalid inputs MUST shows proper messages
- [ ] Every currency number that displayed MUST be formatted to Rupiah standard string, and MUST be spelled in Bahasa; for example:

  `Rp10.000,00`\
  `Sepuluh Ribu Rupiah`
- [ ] Electricity bills token MUST be randomly or procedurally generated, with `{RANDOM_UUID}_{First 8 Digit from SHA_256 of (EPOCH_MILLIS)}_{AMOUNT_OF_BOUGHT_TOKEN}`; for example:

  `aaaa-bb-cc-ddd_2afcb452_50000`\
  if you bought a Rp50.000,00 token

## Additional Information

- Some part of these codes are intentionally made error, and contain bugs; you are REQUIRED to fix these
- It is RECOMMENDED to use advanced Java features and coding method, to get better points; for example: OOP, collection stream, proper data type, SOLID, modularism, etc.
- Beautification of the output SHALL NOT have bonus points, so you SHOULD focus on implementing features instead
- This project SHALL NOT connect to database at this point, so you MUST implement data store on underlying `data.repository` package

---

## Review Form

_Please insert you review here, only after you finished this project, either on completed or due to time-limit!_

> ```{ WRITE YOUR REVIEW HERE }```
