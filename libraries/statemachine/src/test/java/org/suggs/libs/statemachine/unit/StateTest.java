/*
 * StateTest.java created on 28 Aug 2009 18:15:18 by suggitpe for project Libraries - State Machine
 * 
 */
package org.suggs.libs.statemachine.unit;

import org.suggs.libs.statemachine.Action;
import org.suggs.libs.statemachine.State;
import org.suggs.libs.statemachine.StateMachineContext;
import org.suggs.libs.statemachine.StateTransition;
import org.suggs.libs.statemachine.StateMachineException;
import org.suggs.libs.statemachine.impl.StateImpl;
import org.suggs.libs.statemachine.impl.StateTransitionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Test suite for the state implementation.
 * 
 * @author suggitpe
 * @version 1.0 28 Aug 2009
 */
public class StateTest {

    private static final Logger LOG = LoggerFactory.getLogger( StateTest.class );
    private IMocksControl ctrl;

    private StateMachineContext mockContext;
    private StateTransition mockTransitionOne;
    private StateTransition mockTransitionTwo;
    private Action mockAction;

    /** */
    @BeforeClass
    public static void doBeforeClass() {
        LOG.debug( "===================" + StateTest.class.getSimpleName() );
    }

    /** */
    @Before
    public void doBefore() {
        LOG.debug( "------------------- " );
        StateTransitionManager.instance().clearTransitionsFromTransitionManager();
        ctrl = createControl();
        mockContext = ctrl.createMock( StateMachineContext.class );
        mockTransitionOne = ctrl.createMock( StateTransition.class );
        mockTransitionTwo = ctrl.createMock( StateTransition.class );
        mockAction = ctrl.createMock( Action.class );
    }

    /**
     * Simply tests that the state name has been correctly set at state construction
     */
    @Test
    public void stateNameExtraction() {
        final String STATE_NAME = "TestStateForTest";
        State state = new StateImpl( STATE_NAME );

        assertThat( state.getStateName(), equalTo( STATE_NAME ) );
        LOG.debug( "Successfully created state[" + state + "]" );
    }

    /**
     * Tests that when there are valid transitions set up, step will return the correct new end state.
     * 
     * @throws StateMachineException
     */
    @SuppressWarnings("boxing")
    @Test
    public void stepWithValidTransitionsToReturnNewState() throws StateMachineException {
        State state = new StateImpl( "TestState" );
        State endState = new StateImpl( "TestEndState" );

        expect( mockTransitionOne.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionOne.getTransitionName() ).andReturn( "invalidTransition" ).anyTimes();
        expect( mockTransitionTwo.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionTwo.getTransitionName() ).andReturn( "badTransition" ).anyTimes();
        expect( mockTransitionTwo.getEndingState() ).andReturn( endState ).anyTimes();

        expect( mockTransitionOne.evaluateTransitionValidity( mockContext ) ).andReturn( false );
        expect( mockTransitionTwo.evaluateTransitionValidity( mockContext ) ).andReturn( true );

        ctrl.replay();

        StateTransitionManager.instance().addTransitionToManager( mockTransitionOne );
        StateTransitionManager.instance().addTransitionToManager( mockTransitionTwo );

        State newState = state.step( mockContext );

        assertThat( state, not( equalTo( newState ) ) );
        assertThat( endState, equalTo( newState ) );
        LOG.debug( "Checked that the step call returns a different state when there are valid transitions setup" );

        ctrl.verify();
    }

    /**
     * Tests that when there are more than one valid transitions set up, step will give rise to an exception
     * being thrown.
     * 
     * @throws StateMachineException
     */
    @SuppressWarnings("boxing")
    @Test(expected = StateMachineException.class)
    public void stepWithTwoValidTransitionsCausesException() throws StateMachineException {
        State state = new StateImpl( "TestState" );
        State endState = new StateImpl( "TestEndState" );

        expect( mockTransitionOne.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionOne.getTransitionName() ).andReturn( "invalidTransition" ).anyTimes();
        expect( mockTransitionTwo.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionTwo.getTransitionName() ).andReturn( "badTransition" ).anyTimes();
        expect( mockTransitionTwo.getEndingState() ).andReturn( endState ).anyTimes();

        expect( mockTransitionOne.evaluateTransitionValidity( mockContext ) ).andReturn( true );
        expect( mockTransitionTwo.evaluateTransitionValidity( mockContext ) ).andReturn( true );

        ctrl.replay();

        StateTransitionManager.instance().addTransitionToManager( mockTransitionOne );
        StateTransitionManager.instance().addTransitionToManager( mockTransitionTwo );

        State newState = state.step( mockContext );
        LOG.error( "If the code managed to reach here then the test has failed to perform it's role.  Somehow we have managed to let step create  anew state of ["
                   + newState + "]" );

        ctrl.verify();
    }

    /**
     * Tests that when there are transitions set up but that none of them are valid transitions, the same
     * state is returned from the step call.
     * 
     * @throws StateMachineException
     */
    @SuppressWarnings("boxing")
    @Test
    public void stepwithNonValidTransitionsToReturnSelf() throws StateMachineException {
        State state = new StateImpl( "TestState" );

        expect( mockTransitionOne.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionOne.getTransitionName() ).andReturn( "invalidTransition" ).anyTimes();
        expect( mockTransitionTwo.getStartingState() ).andReturn( state ).anyTimes();
        expect( mockTransitionTwo.getTransitionName() ).andReturn( "badTransition" ).anyTimes();

        expect( mockTransitionOne.evaluateTransitionValidity( mockContext ) ).andReturn( false );
        expect( mockTransitionTwo.evaluateTransitionValidity( mockContext ) ).andReturn( false );

        ctrl.replay();

        StateTransitionManager.instance().addTransitionToManager( mockTransitionOne );
        StateTransitionManager.instance().addTransitionToManager( mockTransitionTwo );

        State newState = state.step( mockContext );

        assertThat( state, equalTo( newState ) );
        LOG.debug( "Checked that the step call returns the same state when there are no valid transitions setup" );

        ctrl.verify();
    }

    /**
     * Tests that if there are no valid transitions set up for the state that the call to step will not give
     * rise to exceptions and will simply return the same state.
     * 
     * @throws StateMachineException
     */
    @Test
    public void stepWithNoTransitionsSetUpReturnsSelf() throws StateMachineException {
        ctrl.replay();

        State state = new StateImpl( "TestState" );
        State newState = state.step( mockContext );

        assertThat( state, sameInstance( newState ) );
        LOG.debug( "Checked that the step call returns the same state when there are no transitions setup" );

        ctrl.verify();
    }

    /**
     * Tests that when we have set an entry action on a state and we call execute entry action on the state,
     * that the call is propogated down to the execute on teh underlying action.
     * 
     * @throws StateMachineException
     */
    @Test
    public void executionOfEntryAction() throws StateMachineException {
        mockAction.execute( mockContext );
        expectLastCall().once();

        ctrl.replay();

        StateImpl state = new StateImpl( "TestState" );
        state.setEntryAction( mockAction );
        state.executeEntryAction( mockContext );

        ctrl.verify();
    }

    /**
     * Tests that when we have set an entry action on a state and we call execute entry action on the state,
     * that the call is propogated down to the execute on teh underlying action.
     * 
     * @throws StateMachineException
     */
    @Test
    public void executionOfExitAction() throws StateMachineException {
        mockAction.execute( mockContext );
        expectLastCall().once();

        ctrl.replay();

        StateImpl state = new StateImpl( "TestState" );
        state.setExitAction( mockAction );
        state.executeExitAction( mockContext );

        ctrl.verify();
    }

    /**
     * Tests the that the equals, hashcode and toString methods work correctly
     */
    @Test
    public void equalsIsTrueWithSameObject() {
        StateImpl state = new StateImpl( "helllo" );
        assertThat( state, equalTo( state ) );
    }

    @Test
    public void equalsIsTrueWithSameDataObjects() {
        StateImpl stateA = new StateImpl( "state1" );
        StateImpl stateB = new StateImpl( stateA );
        assertThat( stateA, equalTo( stateB ) );
    }

    @Test
    public void equalsIsFalseAgainstNullObject() {
        StateImpl state = new StateImpl( "helllo" );
        assertFalse( state.equals( null ) );
    }

    @Test
    public void equalsFailsWithDataMismatch() {
        StateImpl stateA = new StateImpl( "State A" );
        StateImpl stateNull = new StateImpl( (String) null );
        StateImpl stateB = new StateImpl( "State B" );

        assertFalse( stateA.equals( new String( "boom!" ) ) );
        assertThat( stateA, not( equalTo( stateB ) ) );
        assertThat( stateA, not( equalTo( stateNull ) ) );

    }

    @SuppressWarnings("boxing")
    @Test
    public void hashcodeProducesMatchWithSameValues() {
        StateImpl state1a = new StateImpl( "state1" );
        StateImpl state1b = new StateImpl( state1a );
        StateImpl state2 = new StateImpl( "state2" );

        assertThat( state1a.hashCode(), equalTo( state1b.hashCode() ) );
        assertThat( state1a.hashCode(), not( equalTo( state2.hashCode() ) ) );
    }

    @SuppressWarnings("boxing")
    @Test
    public void hascodeProducesDifferentResultWithDiffereingValues() {
        StateImpl state1a = new StateImpl( "state1" );
        StateImpl state1b = new StateImpl( (String) null );

        assertThat( state1a.hashCode(), not( equalTo( state1b.hashCode() ) ) );

    }
}
