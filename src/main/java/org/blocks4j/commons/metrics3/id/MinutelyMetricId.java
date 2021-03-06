package org.blocks4j.commons.metrics3.id;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.Timer;
import org.blocks4j.commons.metrics3.MetricType;

import java.util.concurrent.TimeUnit;

public class MinutelyMetricId<METRIC extends Metric> extends TemporalMetricId<METRIC> {

    private MinutelyMetricId(MetricType metricType) {
        this.setMetricType(metricType);
    }

    @Override
    public long truncateTimestamp(long timestamp) {
        return (timestamp / TimeUnit.MINUTES.toMillis(1)) * TimeUnit.MINUTES.toMillis(1);
    }

    public static MinutelyMetricIdBuilder<Counter> createMinutelyCounterIdBuilder() {
        return new MinutelyMetricIdBuilder<>(MetricType.COUNTER);
    }

    public static MinutelyMetricIdBuilder<Meter> createMinutelyMeterIdBuilder() {
        return new MinutelyMetricIdBuilder<>(MetricType.METER);
    }

    public static MinutelyMetricIdBuilder<Timer> createMinutelyTimerIdBuilder() {
        return new MinutelyMetricIdBuilder<>(MetricType.TIMER);
    }

    public static MinutelyMetricIdBuilder<Histogram> createMinutelyHistogramIdBuilder() {
        return new MinutelyMetricIdBuilder<>(MetricType.HISTOGRAM);
    }

    public static class MinutelyMetricIdBuilder<METRIC extends Metric> extends TemporalMetricIdBuilder<METRIC, MinutelyMetricId<METRIC>> {
        private MinutelyMetricIdBuilder(MetricType metricType) {
            super(new MinutelyMetricId<METRIC>(metricType));
            this.expiration(TimeUnit.HOURS.toMillis(1));
        }

        @Override
        public TemporalMetricIdBuilder<METRIC, MinutelyMetricId<METRIC>> expiration(long expiration) {
            if(expiration < TimeUnit.MINUTES.toMillis(1)){
                throw new IllegalStateException("Is this configuration right?");
            }

            return super.expiration(expiration);
        }
    }
}
