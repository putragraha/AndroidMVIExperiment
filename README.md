# MVI Pattern
MVI pattern as architecture for maintaining presentation logic i.e. state of the view
Built with reactive flow using Rx libs

- MVI was Unidirectional Flow based architectural, it is Intent (represent as Action in this project) & State flow from Model to View and vice versa
- There are 3 important components in MVI:
    - Reducer -> to control state changes (Loading state, success state, or failed state)
    - Middleware -> for Side Effect (Actions)
    - Store -> to gather middleware & reducer
