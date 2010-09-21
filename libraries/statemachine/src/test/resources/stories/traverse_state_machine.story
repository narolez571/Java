Events cause state changes within state machine.

Narrative:
In order to correctly understand an entity's state
As a state machine user
I want to correctly represent state (of anything) based on a sequence of events

Scenario: initialised state machine should be in an initial state
Given an unused state machine
When no events are received
Then the state should be Connection:Initial

Scenario: connect event results in connected
Given an unused state machine
When a connect event is received
Then the state should be Connection:Connected

Scenario: connect event followed by disconnect event results in disconnected
Given an unused state machine
When a connect event is received
Then the state should be Connection:Connected
When a disconnect event is received
Then the state should be Connection:Disconnected

Scenario: connect event followed by a connected event results in connected
Given an unused state machine
When a connect event is received
Then the state should be Connection:Connected
When a connect event is received
Then the state should be Connection:Connected
