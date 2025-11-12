# Assignment 6 - UI Testing

For this assignment, I manually wrote a full Playwright test in Java to automate a purchase pathway on the DePaul University Bookstore website. This included setting up the Playwright dependencies in Maven, creating the test structure, writing detailed assertions, and using locators to interact with every element on the page.

This manual approach gave me complete control over tests and the logic behind it. I was able to define exactly what each step should do, as going through Chromium allowed me to thoroughly search for “earbuds,” apply filters, verify product details, add the item to the cart, fill out contact information, and check totals at checkout. Because I wrote the test code myself, I could handle specific cases (like verifying specific prices or checking that the cart correctly updates to “1 item”).

While it was a time-consuming process, I was able to confirm that each assertion matched the webpage content as it should. I would argue that it acts as a more reliable approach than AI-assisted UI testing, as I ran into setup and runtime issues with the MCP server that prevented me from being able to generate tests as a result. Beyond setup, however, I would still favor manual testing as the idea of success or failure with AI-assistance depends primarily on how detailed the prompt would be. This can be shown via the prompt example in the assignment PDF:

> `"Test search for earbuds, filter by color, add to cart, and verify the cart shows 1 item."`

Something like this would have likely been too vague and could have resulted in incomplete or inaccurate tests. The method is likely much faster, but it is not a guaranteed success as the result is only as reliable as the prompt it would have been given.
