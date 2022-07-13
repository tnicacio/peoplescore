package com.tnicacio.peoplescore.score.enums;

public enum Score implements Range {

    INSUFFICIENT("Insuficiente") {
        @Override
        public long initialValue() {
            return 0L;
        }

        @Override
        public long finalValue() {
            return 200L;
        }
    },
    UNACCEPTABLE("Inaceitável") {
        @Override
        public long initialValue() {
            return 201L;
        }

        @Override
        public long finalValue() {
            return 500L;
        }
    },
    ACCEPTABLE("Aceitável") {
        @Override
        public long initialValue() {
            return 501L;
        }

        @Override
        public long finalValue() {
            return 700L;
        }
    },
    RECOMMENDABLE("Recomendável") {
        @Override
        public long initialValue() {
            return 701;
        }

        @Override
        public long finalValue() {
            return 1000;
        }
    };

    private final String description;

    Score(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
