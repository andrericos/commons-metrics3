package org.blocks4j.commons.metrics3.id;

import com.codahale.metrics.Metric;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.blocks4j.commons.metrics3.MetricType;

import java.util.Objects;

public abstract class TemporalMetricId<METRIC extends Metric> implements Cloneable {

    private MetricType metricType;

    private Class<?> ownerClass;

    private String name;

    private long expiration;

    public TemporalMetricId() {

    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (!(object instanceof TemporalMetricId)) {
            return false;
        }

        TemporalMetricId that = (TemporalMetricId) object;

        return Objects.equals(this.metricType, that.metricType) &&
                Objects.equals(this.ownerClass, that.ownerClass) &&
                Objects.equals(this.expiration, that.expiration) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.metricType)
                .append(this.ownerClass)
                .append(this.name)
                .append(this.expiration)
                .hashCode();
    }

    public abstract long truncateTimestamp(long timestamp);

    public MetricType getMetricType() {
        return this.metricType;
    }

    protected void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public Class<?> getOwnerClass() {
        return this.ownerClass;
    }

    protected void setOwnerClass(Class<?> ownerClass) {
        this.ownerClass = ownerClass;
    }

    public String getName() {
        return this.name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public long getExpiration() {
        return expiration;
    }

    protected void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public TemporalMetricId<?> clone() throws CloneNotSupportedException {
        return (TemporalMetricId<?>) super.clone();
    }

    public static abstract class TemporalMetricIdBuilder<METRIC extends Metric, ID extends TemporalMetricId<METRIC>> {
        private ID instance;

        protected TemporalMetricIdBuilder(ID instance) {
            this.instance = instance;
        }

        public TemporalMetricIdBuilder<METRIC, ID> ownerClass(Class<?> ownerClass) {
            this.instance.setOwnerClass(ownerClass);
            return this;
        }

        public TemporalMetricIdBuilder<METRIC, ID> name(String name) {
            this.instance.setName(name);
            return this;
        }

        public TemporalMetricIdBuilder<METRIC, ID> expiration(long expiration) {
            this.instance.setExpiration(expiration);
            return this;
        }


        public ID build() {
            try {
                return (ID) this.instance.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
