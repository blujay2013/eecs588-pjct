// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/protobuf/unittest_lite_imports_nonlite.proto

package protobuf_unittest;

public final class UnittestLiteImportsNonlite {
  private UnittestLiteImportsNonlite() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface TestLiteImportsNonliteOrBuilder
      extends com.google.protobuf.MessageLiteOrBuilder {

    // optional .protobuf_unittest.TestAllTypes message = 1;
    /**
     * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
     */
    boolean hasMessage();
    /**
     * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
     */
    protobuf_unittest.UnittestProto.TestAllTypes getMessage();
  }
  /**
   * Protobuf type {@code protobuf_unittest.TestLiteImportsNonlite}
   */
  public static final class TestLiteImportsNonlite extends
      com.google.protobuf.GeneratedMessageLite
      implements TestLiteImportsNonliteOrBuilder {
    // Use TestLiteImportsNonlite.newBuilder() to construct.
    private TestLiteImportsNonlite(com.google.protobuf.GeneratedMessageLite.Builder builder) {
      super(builder);

    }
    private TestLiteImportsNonlite(boolean noInit) {}

    private static final TestLiteImportsNonlite defaultInstance;
    public static TestLiteImportsNonlite getDefaultInstance() {
      return defaultInstance;
    }

    public TestLiteImportsNonlite getDefaultInstanceForType() {
      return defaultInstance;
    }

    private TestLiteImportsNonlite(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              protobuf_unittest.UnittestProto.TestAllTypes.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = message_.toBuilder();
              }
              message_ = input.readMessage(protobuf_unittest.UnittestProto.TestAllTypes.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(message_);
                message_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static com.google.protobuf.Parser<TestLiteImportsNonlite> PARSER =
        new com.google.protobuf.AbstractParser<TestLiteImportsNonlite>() {
      public TestLiteImportsNonlite parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new TestLiteImportsNonlite(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<TestLiteImportsNonlite> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional .protobuf_unittest.TestAllTypes message = 1;
    public static final int MESSAGE_FIELD_NUMBER = 1;
    private protobuf_unittest.UnittestProto.TestAllTypes message_;
    /**
     * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
     */
    public protobuf_unittest.UnittestProto.TestAllTypes getMessage() {
      return message_;
    }

    private void initFields() {
      message_ = protobuf_unittest.UnittestProto.TestAllTypes.getDefaultInstance();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, message_);
      }
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, message_);
      }
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    /**
     * Protobuf type {@code protobuf_unittest.TestLiteImportsNonlite}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite, Builder>
        implements protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonliteOrBuilder {
      // Construct using protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        message_ = protobuf_unittest.UnittestProto.TestAllTypes.getDefaultInstance();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite getDefaultInstanceForType() {
        return protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite.getDefaultInstance();
      }

      public protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite build() {
        protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite buildPartial() {
        protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite result = new protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.message_ = message_;
        result.bitField0_ = to_bitField0_;
        return result;
      }

      public Builder mergeFrom(protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite other) {
        if (other == protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite.getDefaultInstance()) return this;
        if (other.hasMessage()) {
          mergeMessage(other.getMessage());
        }
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protobuf_unittest.UnittestLiteImportsNonlite.TestLiteImportsNonlite) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional .protobuf_unittest.TestAllTypes message = 1;
      private protobuf_unittest.UnittestProto.TestAllTypes message_ = protobuf_unittest.UnittestProto.TestAllTypes.getDefaultInstance();
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public protobuf_unittest.UnittestProto.TestAllTypes getMessage() {
        return message_;
      }
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public Builder setMessage(protobuf_unittest.UnittestProto.TestAllTypes value) {
        if (value == null) {
          throw new NullPointerException();
        }
        message_ = value;

        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public Builder setMessage(
          protobuf_unittest.UnittestProto.TestAllTypes.Builder builderForValue) {
        message_ = builderForValue.build();

        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public Builder mergeMessage(protobuf_unittest.UnittestProto.TestAllTypes value) {
        if (((bitField0_ & 0x00000001) == 0x00000001) &&
            message_ != protobuf_unittest.UnittestProto.TestAllTypes.getDefaultInstance()) {
          message_ =
            protobuf_unittest.UnittestProto.TestAllTypes.newBuilder(message_).mergeFrom(value).buildPartial();
        } else {
          message_ = value;
        }

        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .protobuf_unittest.TestAllTypes message = 1;</code>
       */
      public Builder clearMessage() {
        message_ = protobuf_unittest.UnittestProto.TestAllTypes.getDefaultInstance();

        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protobuf_unittest.TestLiteImportsNonlite)
    }

    static {
      defaultInstance = new TestLiteImportsNonlite(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protobuf_unittest.TestLiteImportsNonlite)
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}