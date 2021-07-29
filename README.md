# MVI Pattern
MVI pattern is architecture for maintaining presentation logic i.e. state of the view

- MVI was Unidirectional Flow based architectural, it is consist of:
  - Intent (represent as Action in this project) as a trigger from View to do action in ViewModel
  - State, as a data which flowing from Model to View
  - and also Reducer, to map intent to state (Loading state, success state, or failed state)

## Features
- For Rx built use branch `rx-java`
- For Coroutine with Flow built use branch `master` (bonus: Android DataBinding)
