import {shallowMount} from "@vue/test-utils";

import {expect} from 'chai';
import HelloWorld from "../../src/components/HelloWorld";

describe('HelloWorldVue-test', function () {

  let wrapper;
  beforeEach(() => {
    wrapper = shallowMount(HelloWorld, {});
  })

  it('renders props.msg when passed', () => {
    expect(wrapper.vm.$el.querySelectorAll("[type=button]").length).to.equal(3)
  });

  it('init rendering visit test', function () {

  });
});