# MVI Pattern
MVI pattern is architecture for maintaining presentation logic i.e. state of the view

- MVI was Unidirectional Flow based architectural, it is consist of:
  - Intent (represent as Action in this project) as a trigger from View to do action in ViewModel
  - State, as a data which flowing from Model to View
- There are 3 important components in MVI:
    - Reducer -> to control state changes (Loading state, success state, or failed state)
    - Middleware -> for Side Effect (Actions)
    - Store -> to gather middleware & reducer

## Features
- For Rx built use branch `rx-java`
- For Coroutine built use branch `master`
