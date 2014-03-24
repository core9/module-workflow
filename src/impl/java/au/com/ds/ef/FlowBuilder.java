package au.com.ds.ef;

public class FlowBuilder<C extends StatefulContext> {
	private EasyFlow<C> flow;

    public static class ToHolder {
        private EventEnum event;

        private ToHolder(EventEnum event) {
            this.event = event;
        }

        public Transition to(StateEnum state) {
            return new Transition(event, state, false);
        }

        public Transition finish(StateEnum state) {
            return new Transition(event, state, true);
        }
    }

	private FlowBuilder(StateEnum startState) {
        flow = new EasyFlow<C>(startState);
	}

	public static <C extends StatefulContext> FlowBuilder<C> from(StateEnum startState) {
		return new FlowBuilder<C>(startState);
	}

    public static ToHolder on(EventEnum event) {
        return new ToHolder(event);
    }

    public <C1 extends StatefulContext> EasyFlow<C1> transit(Transition... transitions) {
        return transit(false, transitions);
    }

	@SuppressWarnings("unchecked")
	public <C1 extends StatefulContext> EasyFlow<C1> transit(boolean skipValidation, Transition... transitions) {
        for (Transition transition : transitions) {
            transition.setStateFrom(flow.getStartState());
        }
		flow.processAllTransitions(skipValidation);

        return (EasyFlow<C1>) flow;
	}
}
