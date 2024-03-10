# Design Decisions

# Design Changes

_I have not included additional Design Decision code snippets as they are the same code snippets previously shown_

## Factory Pattern

### Code Smell

**Duplicate Code**

UI classes contained many **duplicate** methods and fields. Most of the functionality for the duplicate code was the creation of UI elements.

  

### Design Pattern

The Factory pattern was the perfect pattern to solve this issue, since the duplicate code pertained to the creation of UI elements. The creation of those UI elements was then moved into a factory that concerned itself with the creation of each individual UI element.

  

### Justification

Duplicate code reduced maintainability and scalability of the code base, as changing how the application looked required you to modify many classes. Implementing new features would also become more difficult as the feature would have to be implemented multiple times.

The Factory pattern helped extract the duplicate functionality into a single class, which is easy to modify and scale with the application. This also promoted single responsibility by handling the creation of UI elements. Allowing the UI classes to worry about its usage rather than its creation.

  

## MVC Pattern

### Code Smell

**Bloaters**

UI classes were **Bloaters**. Containing many methods, fields and lines of code.

Each UI class contained methods for user interaction, data storage and retrieval, verification and displaying UI elements.

  

### Design Pattern

The Model-View-Controller pattern was used to extract functionality from a single bloater class into smaller classes. These are the model, view and controller classes.

The model contains the data for the specific UI.

The view displays the data and receives user interaction.

The controller formats the data to be displayed and handles user interaction to modify the model and update the view as needed.

  

### Justification

The MVC pattern was used since it was the perfect pattern to decouple data, logic and visuals into individual elements. The pattern helps improve the maintainability and scalability of UI whilst promoting single responsibility between different elements.

This pattern helped reduce the size of the **Bloater** classes by extracting their functionality into separate classes.