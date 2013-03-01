require 'torquebox-messaging'

class Processor < TorqueBox::Messaging::MessageProcessor
  include TorqueBox::Injectors

  def initialize
    @queue = fetch('/queue/foo')
  end

  def on_message(body)
    puts "r #{body}"
    sleep(1)
    @queue.publish("r #{Time.now}", :encoding => :edn)
  end
end
