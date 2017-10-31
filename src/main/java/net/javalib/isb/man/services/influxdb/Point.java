package net.javalib.isb.man.services.influxdb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;


public class Point {
    private String measurement;
    private Map<String, String> tags;
    private Long time;
    private TimeUnit precision = TimeUnit.NANOSECONDS;
    private Map<String, Object> fields;

    private static final Escaper FIELD_ESCAPER = Escapers.builder().addEscape('"', "\\\"").build();
    private static final Escaper KEY_ESCAPER = Escapers.builder().addEscape(' ', "\\ ").addEscape(',', "\\,").addEscape('=', "\\=").build();

    Point() {
    }

    public static Builder measurement(final String measurement) {
        return new Builder(measurement);
    }

    public static final class Builder {
        private final String measurement;
        private final Map<String, String> tags = Maps.newTreeMap(Ordering.natural());
        private Long time;
        private TimeUnit precision = TimeUnit.NANOSECONDS;
        private final Map<String, Object> fields = Maps.newTreeMap(Ordering.natural());

        Builder(final String measurement) {
            this.measurement = measurement;
        }

        public Builder tag(final String tagName, final String value) {
            Preconditions.checkArgument(tagName != null);
            Preconditions.checkArgument(value != null);
            tags.put(tagName, value);
            return this;
        }

        public Builder tag(final Map<String, String> tagsToAdd) {
            for (Entry<String, String> tag : tagsToAdd.entrySet()) {
                tag(tag.getKey(), tag.getValue());
            }
            return this;
        }

        public Builder addField(final String field, final boolean value) {
            fields.put(field, value);
            return this;
        }

        public Builder addField(final String field, final long value) {
            fields.put(field, value);
            return this;
        }

        public Builder addField(final String field, final double value) {
            fields.put(field, value);
            return this;
        }

        public Builder addField(String field, Number value) {
            fields.put(field, value);
            return this;
        }

        public Builder addField(final String field, final String value) {
            if (value == null) {
                throw new IllegalArgumentException("Field value cannot be null");
            }

            fields.put(field, value);
            return this;
        }

        public Builder fields(final Map<String, Object> fieldsToAdd) {
            this.fields.putAll(fieldsToAdd);
            return this;
        }

        public Builder time(final long timeToSet, final TimeUnit precisionToSet) {
            Preconditions.checkNotNull(precisionToSet, "Precision must be not null!");
            this.time = timeToSet;
            this.precision = precisionToSet;
            return this;
        }

        public Point build() {
            Preconditions
                    .checkArgument(!Strings.isNullOrEmpty(this.measurement), "Point name must not be null or empty.");
            Preconditions.checkArgument(this.fields.size() > 0, "Point must have at least one field specified.");
            Point point = new Point();
            point.setFields(this.fields);
            point.setMeasurement(this.measurement);
            if (this.time != null) {
                point.setTime(this.time);
                point.setPrecision(this.precision);
            } else {
                point.setTime(System.currentTimeMillis());
                point.setPrecision(TimeUnit.MILLISECONDS);
            }
            point.setTags(this.tags);
            return point;
        }
    }

    void setMeasurement(final String measurement) {
        this.measurement = measurement;
    }

    void setTime(final Long time) {
        this.time = time;
    }

    void setTags(final Map<String, String> tags) {
        this.tags = tags;
    }

    Map<String, String> getTags() {
        return this.tags;
    }

    void setPrecision(final TimeUnit precision) {
        this.precision = precision;
    }

    void setFields(final Map<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Point [name=");
        builder.append(this.measurement);
        builder.append(", time=");
        builder.append(this.time);
        builder.append(", tags=");
        builder.append(this.tags);
        builder.append(", precision=");
        builder.append(this.precision);
        builder.append(", fields=");
        builder.append(this.fields);
        builder.append("]");
        return builder.toString();
    }

    public String lineProtocol() {
        final StringBuilder sb = new StringBuilder();
        sb.append(KEY_ESCAPER.escape(this.measurement));
        sb.append(concatenatedTags());
        sb.append(concatenateFields());
        sb.append(formatedTime());
        return sb.toString();
    }

    private StringBuilder concatenatedTags() {
        final StringBuilder sb = new StringBuilder();
        for (Entry<String, String> tag : this.tags.entrySet()) {
            sb.append(",");
            sb.append(KEY_ESCAPER.escape(tag.getKey())).append("=").append(KEY_ESCAPER.escape(tag.getValue()));
        }
        sb.append(" ");
        return sb;
    }

    private StringBuilder concatenateFields() {
        final StringBuilder sb = new StringBuilder();
        final int fieldCount = this.fields.size();
        int loops = 0;

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
        numberFormat.setMaximumFractionDigits(340);
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumFractionDigits(1);

        for (Entry<String, Object> field : this.fields.entrySet()) {
            loops++;
            Object value = field.getValue();
            if (value == null) {
                continue;
            }

            sb.append(KEY_ESCAPER.escape(field.getKey())).append("=");
            if (value instanceof String) {
                String stringValue = (String) value;
                sb.append("\"").append(FIELD_ESCAPER.escape(stringValue)).append("\"");
            } else if (value instanceof Number) {
                if (value instanceof Double || value instanceof Float || value instanceof BigDecimal) {
                    sb.append(numberFormat.format(value));
                } else {
                    sb.append(value).append("i");
                }
            } else {
                sb.append(value);
            }

            if (loops < fieldCount) {
                sb.append(",");
            }
        }

        return sb;
    }

    private StringBuilder formatedTime() {
        final StringBuilder sb = new StringBuilder();
        if (null == this.time) {
            this.time = this.precision.convert(System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }
        sb.append(" ").append(TimeUnit.NANOSECONDS.convert(this.time, this.precision));
        return sb;
    }

}